package com.binance.api.client;

import com.binance.api.client.config.BinanceApiConfig;
import com.binance.api.client.impl.BinanceApiAsyncExecutorRestClientImpl;
import com.binance.api.client.impl.BinanceApiAsyncRestClientAdapter;
import com.binance.api.client.impl.BinanceApiAsyncRestClientImpl;

import java.util.concurrent.ExecutorService;

public class BinanceApiClientExtensionFactory {
    /**
     * API Key
     */
    private BinanceApiClientFactory factory;

    /**
     * Instantiates a new binance api client factory.
     *
     * @param apiKey the API key
     * @param secret the Secret
     */
    private BinanceApiClientExtensionFactory(String apiKey, String secret) {
        factory = BinanceApiClientFactory.newInstance(apiKey, secret);
    }

    /**
     * Creates a new asynchronous/non-blocking REST client that uses Executor Services for handling
     * the request and response threading.
     *
     * @param requestService
     * @param responseService
     * @return
     */
    public BinanceApiAsyncExecutorRestClient newAsyncExecutorRestClient(ExecutorService requestService,
                                                                        ExecutorService responseService) {
        return new BinanceApiAsyncExecutorRestClientImpl(factory.newRestClient(), requestService, responseService);
    }

    /**
     * Creates a new asynchronous/non-blocking REST client.
     */
    public BinanceApiAsyncRestClient newAsyncRestClient(ExecutorService requestService,
                                                        ExecutorService responseService) {
        return new BinanceApiAsyncRestClientAdapter(newAsyncExecutorRestClient(requestService, responseService));
    }
}
