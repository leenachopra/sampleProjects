package restapis;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;

public class RestapiClient {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    public static final String APPLICATION_JSON = "application/json";

    public HttpResponse doPost(String wsUrl, String jsonString) throws IOException {
        HttpPost request = new HttpPost(wsUrl);
        StringEntity entity = new StringEntity(jsonString);
        request.addHeader("content-type", APPLICATION_JSON);
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        return response;
    }

    public HttpResponse doGet(String ws2Url, String projectName) throws IOException {
        HttpGet request = new HttpGet(ws2Url + projectName.toLowerCase());
        request.addHeader("accept", APPLICATION_JSON);
        HttpResponse httpResponse = httpClient.execute(request);

        return httpResponse;
    }


}
