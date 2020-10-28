import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientTest {

    private static final String SENDURL = "http://127.0.0.1:8088/api/hello";

    public static void main(String[] args) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(SENDURL);
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(500)
                    .setConnectTimeout(500)
                    .build();
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.format("请求的结果为 %s", EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null !=response){
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
