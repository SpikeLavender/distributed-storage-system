package com.natsumes;

import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ElasticsearchTest {

    private static final String HOST_LIST = "192.168.137.156:9200,192.168.137.158:9200,192.168.137.159:9200";

    private RestHighLevelClient client;

    //private RestClient restClient;

    private final static String INDEX_NAME = "elasticsearch_test";

    @Before
    public void setUp() {
        client = new RestHighLevelClient(RestClient.builder(getHosts()));
        restClient = RestClient.builder(getHosts()).build();
    }

    private HttpHost[] getHosts() {
        String[] split = HOST_LIST.split(",");
        HttpHost[] httpHosts = new HttpHost[split.length];
        for (int i = 0; i < split.length; i++) {
            String item = split[i];
            httpHosts[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        return httpHosts;
    }

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
        createIndexRequest.settings(Settings.builder().put("number_of_shards", "1").put("number_of_replicas", "0").build());
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("properties")
                .startObject()
                .field("studymodel")
                .startObject()
                .field("index", "true")
                .field("type", "keyword")
                .endObject()
                .field("name")
                .startObject()
                .field("index", "true")
                .field("type", "integer")
                .endObject()
                .field("description")
                .startObject()
                .field("index", "true")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .field("pic")
                .startObject()
                .field("index", "false")
                .field("type", "text")
                .endObject()
                .endObject()
                .endObject();
        createIndexRequest.mapping(builder);

        IndicesClient indices = client.indices();
        CreateIndexResponse indexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);
        boolean acknowledged = indexResponse.isAcknowledged();
        System.out.println(acknowledged);
        Assert.assertTrue(acknowledged);
    }

    @Test
    public void testAddDoc() throws IOException {
        //创建索引请求对象
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
        indexRequest.id("1");
        //文档内容 准备json数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "spring cloud实战");
        jsonMap.put("description", "本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。");
        jsonMap.put("studymodel", "201001");
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonMap.put("timestamp", dateFormat.format(new Date()));
        jsonMap.put("price", 5.6f);
        indexRequest.source(jsonMap);
        //通过client进行http的请求
        IndexResponse indexResponse = client.index(indexRequest,RequestOptions.DEFAULT);
        DocWriteResponse.Result result = indexResponse.getResult();
        System.out.println(result);
        Assert.assertEquals(DocWriteResponse.Result.CREATED, result);
    }
    //查询文档
    @Test
    public void testGetDoc() throws IOException {
        testAddDoc();
        //查询请求对象
        GetRequest getRequest = new GetRequest(INDEX_NAME,"1");
        GetResponse getResponse = client.get(getRequest,RequestOptions.DEFAULT);
        //得到文档的内容
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        System.out.println(sourceAsMap);
        Assert.assertEquals("201001", sourceAsMap.get("studymodel"));
    }

    @Test
    public void testSearchAll() throws Exception{
        testAddDoc();
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery())
                .fetchSource(new String[] {"name", "studymodel", "price", "timestamp"}, new String[]{});
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        TotalHits totalHits = hits.getTotalHits();
        SearchHit[] searchHits = hits.getHits();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SearchHit hit : searchHits) {
            String id = hit.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            //由于前边设置了源文档字段过虑，这时description是取不到的
            String description = (String) sourceAsMap.get("description");
            //学习模式
            String studymodel = (String) sourceAsMap.get("studymodel");
            //价格
            Double price = (Double) sourceAsMap.get("price");
            // 日期
            Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }


    @After
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest indexRequest = new DeleteIndexRequest(INDEX_NAME);
        IndicesClient indices = client.indices();
        AcknowledgedResponse response = indices.delete(indexRequest, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        Assert.assertTrue(acknowledged);
    }
}
