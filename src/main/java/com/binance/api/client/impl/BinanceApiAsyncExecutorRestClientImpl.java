package com.binance.api.client.impl;

import com.binance.api.client.BinanceApiAsyncExecutorRestClient;
import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.*;
import com.binance.api.client.domain.event.ListenKey;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.ServerTime;
import com.binance.api.client.domain.market.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * Implementation of BinanceApiAsyncExecutorRestClient using ExecutorServices for handling request and responses.
 */
public class BinanceApiAsyncExecutorRestClientImpl implements BinanceApiAsyncExecutorRestClient {
    private final BinanceApiRestClient client;
    private final ExecutorService requestService;
    private final ExecutorService responseService;

    public BinanceApiAsyncExecutorRestClientImpl(BinanceApiRestClient client, ExecutorService requestService, ExecutorService responseService) {
        this.client = client;
        this.requestService = requestService;
        this.responseService = responseService;
    }

    private <T> CompletableFuture<T> invoke(BinanceApiCallback<T> callback, Supplier<T> t){
        CompletableFuture<T> f = CompletableFuture.supplyAsync(t, requestService);
        if(callback != null){
            f = f.thenApplyAsync(v -> {
                callback.onResponse(v);
                return v;
            }, responseService);

            f.exceptionallyAsync(throwable -> {
                callback.onFailure(throwable);
                return null;
            }, responseService);
        }
        return f;
    }

    @Override
    public CompletableFuture<Void> ping(BinanceApiCallback<Void> callback) {
        return invoke(callback,() -> {
            client.ping();
            return null;
        });
    }

    @Override
    public CompletableFuture<ServerTime> getServerTime(BinanceApiCallback<ServerTime> callback) {
        return invoke(callback, () -> {
            ServerTime t = new ServerTime();
            t.setServerTime(client.getServerTime());
            return t;
        });
    }

    @Override
    public CompletableFuture<ExchangeInfo> getExchangeInfo(BinanceApiCallback<ExchangeInfo> callback) {
        return invoke(callback, () -> client.getExchangeInfo());
    }

    @Override
    public CompletableFuture<List<Asset>> getAllAssets(BinanceApiCallback<List<Asset>> callback) {
        return invoke(callback, () -> client.getAllAssets());
    }

    @Override
    public CompletableFuture<OrderBook> getOrderBook(String symbol, Integer limit, BinanceApiCallback<OrderBook> callback) {
        return invoke(callback, () -> client.getOrderBook(symbol,limit));
    }

    @Override
    public CompletableFuture<List<TradeHistoryItem>> getTrades(String symbol, Integer limit, BinanceApiCallback<List<TradeHistoryItem>> callback) {
        return invoke(callback, () -> client.getTrades(symbol, limit));
    }

    @Override
    public CompletableFuture<List<TradeHistoryItem>> getHistoricalTrades(String symbol, Integer limit, Long fromId, BinanceApiCallback<List<TradeHistoryItem>> callback) {
        return invoke(callback, () -> client.getHistoricalTrades(symbol, limit, fromId));
    }

    @Override
    public CompletableFuture<List<AggTrade>> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<AggTrade>> callback) {
        return invoke(callback, () -> client.getAggTrades(symbol,fromId,limit,startTime,endTime));
    }

    @Override
    public CompletableFuture<List<AggTrade>> getAggTrades(String symbol, BinanceApiCallback<List<AggTrade>> callback) {
        return invoke(callback, () -> client.getAggTrades(symbol));
    }

    @Override
    public CompletableFuture<List<Candlestick>> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<Candlestick>> callback) {
        return invoke(callback, () -> client.getCandlestickBars(symbol,interval,limit,startTime, endTime));
    }

    @Override
    public CompletableFuture<List<Candlestick>> getCandlestickBars(String symbol, CandlestickInterval interval, BinanceApiCallback<List<Candlestick>> callback) {
        return invoke(callback, () -> client.getCandlestickBars(symbol, interval));
    }

    @Override
    public CompletableFuture<TickerStatistics> get24HrPriceStatistics(String symbol, BinanceApiCallback<TickerStatistics> callback) {
        return invoke(callback, () -> client.get24HrPriceStatistics(symbol));
    }

    @Override
    public CompletableFuture<List<TickerStatistics>> getAll24HrPriceStatistics(BinanceApiCallback<List<TickerStatistics>> callback) {
        return invoke(callback, () -> client.getAll24HrPriceStatistics());
    }

    @Override
    public CompletableFuture<List<TickerPrice>> getAllPrices(BinanceApiCallback<List<TickerPrice>> callback) {
        return invoke(callback, () -> client.getAllPrices());
    }

    @Override
    public CompletableFuture<TickerPrice> getPrice(String symbol, BinanceApiCallback<TickerPrice> callback) {
        return invoke(callback, () -> client.getPrice(symbol));
    }

    @Override
    public CompletableFuture<List<BookTicker>> getBookTickers(BinanceApiCallback<List<BookTicker>> callback) {
        return invoke(callback, () -> client.getBookTickers());
    }

    @Override
    public CompletableFuture<NewOrderResponse> newOrder(NewOrder order, BinanceApiCallback<NewOrderResponse> callback) {
        return invoke(callback, () -> client.newOrder(order));
    }

    @Override
    public CompletableFuture<Void> newOrderTest(NewOrder order, BinanceApiCallback<Void> callback) {
        return invoke(callback, () -> {
            client.newOrderTest(order);
            return null;
        });
    }

    @Override
    public CompletableFuture<Order> getOrderStatus(OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback) {
        return invoke(callback, () -> client.getOrderStatus(orderStatusRequest));
    }

    @Override
    public CompletableFuture<CancelOrderResponse> cancelOrder(CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback) {
        return invoke(callback, () -> client.cancelOrder(cancelOrderRequest));
    }

    @Override
    public CompletableFuture<List<Order>> getOpenOrders(OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
        return invoke(callback, () -> client.getOpenOrders(orderRequest));
    }

    @Override
    public CompletableFuture<List<Order>> getAllOrders(AllOrdersRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
        return invoke(callback, () -> client.getAllOrders(orderRequest));
    }

    @Override
    public CompletableFuture<Account> getAccount(Long recvWindow, Long timestamp, BinanceApiCallback<Account> callback) {
        return invoke(callback, () -> client.getAccount(recvWindow, timestamp));
    }

    @Override
    public CompletableFuture<Account> getAccount(BinanceApiCallback<Account> callback) {
        return invoke(callback, () -> client.getAccount());
    }

    @Override
    public CompletableFuture<List<Trade>> getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp, BinanceApiCallback<List<Trade>> callback) {
        return invoke(callback, () -> client.getMyTrades(symbol, limit, fromId, recvWindow, timestamp));
    }

    @Override
    public CompletableFuture<List<Trade>> getMyTrades(String symbol, Integer limit, BinanceApiCallback<List<Trade>> callback) {
        return invoke(callback, () -> client.getMyTrades(symbol, limit));
    }

    @Override
    public CompletableFuture<List<Trade>> getMyTrades(String symbol, BinanceApiCallback<List<Trade>> callback) {
        return invoke(callback, () -> client.getMyTrades(symbol));
    }

    @Override
    public CompletableFuture<WithdrawResult> withdraw(String asset, String address, String amount, String name, String addressTag, BinanceApiCallback<WithdrawResult> callback) {
        return invoke(callback, () -> client.withdraw(asset, address, amount, name, addressTag));
    }

    @Override
    public CompletableFuture<DepositHistory> getDepositHistory(String asset, BinanceApiCallback<DepositHistory> callback) {
        return invoke(callback, () -> client.getDepositHistory(asset));
    }

    @Override
    public CompletableFuture<WithdrawHistory> getWithdrawHistory(String asset, BinanceApiCallback<WithdrawHistory> callback) {
        return invoke(callback, () -> client.getWithdrawHistory(asset));
    }

    @Override
    public CompletableFuture<DepositAddress> getDepositAddress(String asset, BinanceApiCallback<DepositAddress> callback) {
        return invoke(callback, () -> client.getDepositAddress(asset));
    }

    @Override
    public CompletableFuture<ListenKey> startUserDataStream(BinanceApiCallback<ListenKey> callback) {
        return invoke(callback, () -> {
            client.startUserDataStream();
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> keepAliveUserDataStream(String listenKey, BinanceApiCallback<Void> callback) {
        return invoke(callback, () -> {
            client.keepAliveUserDataStream(listenKey);
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> closeUserDataStream(String listenKey, BinanceApiCallback<Void> callback) {
        return invoke(callback, () -> {
            client.closeUserDataStream(listenKey);
            return null;
        });
    }
}