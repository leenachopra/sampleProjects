package restapis;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class RestapiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestapiClient.class);
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    public static final String APPLICATION_JSON = "application/json";
    private String cookies;
    private final String USER_AGENT = "Mozilla/5.0";


    public HttpResponse sendGet(String ws2Url, String projectName) {
        HttpGet getRqst = new HttpGet(ws2Url + projectName.toLowerCase());
        getRqst.addHeader("accept", APPLICATION_JSON);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(getRqst);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getRqst.abort();
        }

        return httpResponse;
    }

    public HttpResponse sendPost(String wsUrl, String jsonString) throws IOException {
        HttpPost postRqst = new HttpPost(wsUrl);
        StringEntity entity = new StringEntity(jsonString);
        postRqst.addHeader("content-type", APPLICATION_JSON);
        postRqst.setEntity(entity);
        HttpResponse response = httpClient.execute(postRqst);
        return response;
    }

    public HttpResponse sendPost(String url, List<NameValuePair> postParams, String referer)
            throws Exception {

        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("Host", "accounts.google.com");
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        post.setHeader("Accept-Language", "en-US,en;q=0.5");
        post.setHeader("Cookie", getCookies());
        post.setHeader("Connection", "keep-alive");
        post.setHeader("Referer", referer);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        post.setEntity(new UrlEncodedFormEntity(postParams));

        HttpResponse response = httpClient.execute(post);
        int responseCode = response.getStatusLine().getStatusCode();

        LOGGER.info("\nSending 'POST' request to URL : " + url);
        LOGGER.info("Post parameters : " + postParams);
        LOGGER.info("Response Code : " + responseCode);


        // Get hold of the response entity
        HttpEntity entity = response.getEntity();
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(entity.getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        //LOGGER.info(result.toString());
        return response;
    }

    public String getPageContent(String url) throws Exception {

        HttpGet request = new HttpGet(url);

        request.setHeader("User-Agent", USER_AGENT);
        request.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Language", "en-US,en;q=0.5");

        HttpResponse response = httpClient.execute(request);
        int responseCode = response.getStatusLine().getStatusCode();

        LOGGER.info("\nSending 'GET' request to URL : " + url);
        LOGGER.info("Response Code : " + responseCode);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        // set cookies
        setCookies(response.getFirstHeader("Set-Cookie") == null ? "" :
                response.getFirstHeader("Set-Cookie").toString());

        return result.toString();
    }

    public List<NameValuePair> getFormParams(
            String html, String username, String password)
            throws UnsupportedEncodingException {

        LOGGER.info("Extracting form's data...");

        Document doc = Jsoup.parse(html);

        // Google form id
        Element loginform = doc.getElementById("gaia_loginform");
        Elements inputElements = loginform.getElementsByTag("input");

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();

        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("Email"))
                value = username;
            else if (key.equals("Passwd"))
                value = password;

            paramList.add(new BasicNameValuePair(key, value));

        }

        return paramList;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }


    public void closeConnection() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
