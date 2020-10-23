package chand.springframework.mssc.breweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer maxTotalConnection;
    private final Integer maxPerRout;
    private final Integer maxConnectionTimeout;
    private final Integer socketTimeout;

    public BlockingRestTemplateCustomizer(@Value("${sfg.maxTotalConnection}") Integer maxTotalConnection,
                                          @Value("${sfg.maxPerRout}") Integer maxPerRout,
                                          @Value("${sfg.maxConnectionTimeout}") Integer maxConnectionTimeout,
                                          @Value("${sfg.SocketTimeout}") Integer socketTimeout) {
        this.maxTotalConnection = maxTotalConnection;
        this.maxPerRout = maxPerRout;
        this.maxConnectionTimeout = maxConnectionTimeout;
        this.socketTimeout = socketTimeout;
    }


    public ClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConnection); //maximum thread
        connectionManager.setDefaultMaxPerRoute(maxPerRout);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(maxConnectionTimeout)
                .setSocketTimeout(socketTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
