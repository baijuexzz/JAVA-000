import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class OkHttpClientTest {



    private static final String SENDURL="http://127.0.0.1:8088/api/hello";

    public static void main(String[] args) {

        OkHttpClient okHttpClient = null;
        Response response=null;
        try {
            okHttpClient=new OkHttpClient();
            Request request = new Request.Builder()
                    .url(SENDURL)
                    .get()
                    .build();
             response = okHttpClient.newCall(request).execute();
            System.out.format("请求的结果为 %s", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (okHttpClient != null) {
                okHttpClient.clone();
            }
            if (response !=null){
                response.close();
            }
        }

    }
}
