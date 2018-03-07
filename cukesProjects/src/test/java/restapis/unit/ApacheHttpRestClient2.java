package restapis.unit;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restapis.RestapiClient;


import static org.apache.http.protocol.HTTP.USER_AGENT;
import static org.junit.Assert.assertEquals;

/**
 * This example demonstrates an alternative way to call a URL
 * using the Apache HttpClient HttpGet and HttpResponse
 * classes.
 *
 * I copied the guts of this example from the Apache HttpClient
 * ClientConnectionRelease class, and decided to leave all the
 * try/catch/finally handling in the class. You don't have to catch
 * all the exceptions individually like this, I just left the code
 * as-is to demonstrate all the possible exceptions.
 *
 * Apache HttpClient: http://hc.apache.org/httpclient-3.x/
 *
 */
public class ApacheHttpRestClient2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApacheHttpRestClient2.class);

    @Test
    public void apacheGetTest() {

        RestapiClient restapiClient = new RestapiClient();
        // Execute HTTP request
        HttpResponse httpResponse = restapiClient.sendGet("http://www.google.com/search?q=httpClient", "apacheRest1");

        LOGGER.info("----------------------------------------");
        LOGGER.info(httpResponse.getStatusLine().toString());
        LOGGER.info("----------------------------------------");

        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + responseCode);
        }
        assertEquals("Response code is not expected", 200, responseCode);
    }

    @Test
    public void apachePostTest() throws Exception {

        String url = "https://accounts.google.com/ServiceLoginAuth";
        String gmail = "https://mail.google.com/mail/";
        String referer = "https://accounts.google.com/ServiceLoginAuth";
        RestapiClient restapiClient = new RestapiClient();
        // make sure cookies is turn on
        CookieHandler.setDefault(new CookieManager());
        String page = restapiClient.getPageContent(url);
        //LOGGER.info("page::" + page);


        List<NameValuePair> postParams =
                restapiClient.getFormParams(page, "username", "password");

        // Execute HTTP request
        HttpResponse httpResponse = restapiClient.sendPost(url, postParams, referer);

        //String result = restapiClient.getPageContent(gmail);
        //LOGGER.info("Result::" + result);
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + responseCode);
        }
        assertEquals("Response code is not expected", 200, responseCode);
    }
}
