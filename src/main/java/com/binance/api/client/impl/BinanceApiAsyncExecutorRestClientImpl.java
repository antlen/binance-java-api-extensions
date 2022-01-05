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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
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

    private <T> Future<T> invoke(BinanceApiCallback<T> callback, Supplier<T> t){
        return requestService.submit(() -> {
            try {
                T v = t.get();
                responseService.submit(() -> callback.onResponse(v));
                return v;
            }catch (Exception e){
                responseService.submit(() -> callback.onFailure(e));
                throw e;
            }
        });
    }

    @Override
    public Future<Void> ping(BinanceApiCallback<Void> callback) {
        return invoke(callback,() -> {
            client.ping();
            return null;
        });
    }

    @Override
    public Future<ServerTime> getServerTime(BinanceApiCallback<ServerTime> callback) {
        return invoke(callback, () -> {
            ServerTime t = new ServerTime();
            t.setServerTime(client.getServerTime());
            return t;
        });
    }

    @Override
    public Future<ExchangeInfo> getExchangeInfo(BinanceApiCallback<ExchangeInfo> callback) {
        return invoke(callback, () -> client.getExchangeInfo());
    }

    @Override
    public Future<List<Asset>> getAllAssets(BinanceApiCallback<List<Asset>> callback) {
        return invoke(callback, () -> client.getAllAssets());
    }

    @Override
    public Future<OrderBook> getOrderBook(String symbol, Integer limit, BinanceApiCallback<OrderBook> callback) {
        return invoke(callback, () -> client.getOrderBook(symbol,limit));
    }

    @Override
    public Future<List<TradeHistoryItem>> getTrades(String symbol, Integer limit, BinanceApiCallback<List<TradeHistoryItem>> callback) {
        return invoke(callback, () -> client.getTrades(symbol, limit));
    }

    @Override
    public Future<List<TradeHistoryItem>> getHistoricalTrades(String symbol, Integer limit, Long fromId, BinanceApiCallback<List<TradeHistoryItem>> callback) {
        return invoke(callback, () -> client.getHistoricalTrades(symbol, limit, fromId));
    }

    @Override
    public Future<List<AggTrade>> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<AggTrade>> callback) {
        return invoke(callback, () -> client.getAggTrades(symbol,fromId,limit,startTime,endTime));
    }

    @Override
    public Future<List<AggTrade>> getAggTrades(String symbol, BinanceApiCallback<List<AggTrade>> callback) {
        return invoke(callback, () -> client.getAggTrades(symbol));
    }

    @Override
    public Future<List<Candlestick>> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<Candlestick>> callback) {
        return invoke(callback, () -> client.getCandlestickBars(symbol,interval,limit,startTime, endTime));
    }

    @Override
    public Future<List<Candlestick>> getCandlestickBars(String symbol, CandlestickInterval interval, BinanceApiCallback<List<Candlestick>> callback) {
        return invoke(callback, () -> client.getCandlestickBars(symbol, interval));
    }

    @Override
    public Future<TickerStatistics> get24HrPriceStatistics(String symbol, BinanceApiCallback<TickerStatistics> callback) {
        return invoke(callback, () -> client.get24HrPriceStatistics(symbol));
    }

    @Override
    public Future<List<TickerStatistics>> getAll24HrPriceStatistics(BinanceApiCallback<List<TickerStatistics>> callback) {
        return invoke(callback, () -> client.getAll24HrPriceStatistics());
    }

    @Override
    public Future<List<TickerPrice>> getAllPrices(BinanceApiCallback<List<TickerPrice>> callback) {
        return invoke(callback, () -> client.getAllPrices());
    }

    @Override
    public Future<TickerPrice> getPrice(String symbol, BinanceApiCallback<TickerPrice> callback) {
        return invoke(callback, () -> client.getPrice(symbol));
    }

    @Override
    public Future<List<BookTicker>> getBookTickers(BinanceApiCallback<List<BookTicker>> callback) {
        return invoke(callback, () -> client.getBookTickers());
    }

    @Override
    public Future<NewOrderResponse> newOrder(NewOrder order, BinanceApiCallback<NewOrderResponse> callback) {
        return invoke(callback, () -> client.newOrder(order));
    }

    @Override
    public Future<Void> newOrderTest(NewOrder order, BinanceApiCallback<Void> callback) {
        return invoke(callback, () -> {
            client.newOrderTest(order);
            return null;
        });
    }

    @Override
    public Future<Order> getOrderStatus(OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback) {
        return invoke(callback, () -> client.getOrderStatus(orderStatusRequest));
    }

    @Override
    public Future<CancelOrderResponse> cancelOrder(CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback) {
        return invoke(callback, () -> client.cancelOrder(cancelOrderRequest));
    }

    @Override
    public Future<List<Order>> getOpenOrders(OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
        return invoke(callback, () -> client.getOpenOrders(orderRequest));
    }

    @Override
    public Future<List<Order>> getAllOrders(AllOrdersRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
        return invoke(callback, () -> client.getAllOrders(orderRequest));
    }

    @Override
    public Future<Account> getAccount(Long recvWindow, Long timestamp, BinanceApiCallback<Account> callback) {
        return invoke(callback, () -> client.getAccount(recvWindow, timestamp));
    }

    @Override
    public Future<Account> getAccount(BinanceApiCallback<Account> callback) {
        return invoke(callback, () -> client.getAccount());
    }

    @Override
    public Future<List<Trade>> getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp, BinanceApiCallback<List<Trade>> callback) {
        return invoke(callback, () -> client.getMyTrades(symbol, limit, fromId, recvWindow, timestamp));
    }

    @Override
    public Future<List<Trade>> getMyTrades(String symbol, Integer limit, BinanceApiCallback<List<Trade>> callback) {
        return invoke(callback, () -> client.getMyTrades(symbol, limit));
    }

    @Override
    public Future<List<Trade>> getMyTrades(String symbol, BinanceApiCallback<List<Trade>> callback) {
        return invoke(callback, () -> client.getMyTrades(symbol));
    }

    @Override
    public Future<WithdrawResult> withdraw(String asset, String address, String amount, String name, String addressTag, BinanceApiCallback<WithdrawResult> callback) {
        return invoke(callback, () -> client.withdraw(asset, address, amount, name, addressTag));
    }

    @Override
    public Future<DepositHistory> getDepositHistory(String asset, BinanceApiCallback<DepositHistory> callback) {
        return invoke(callback, () -> client.getDepositHistory(asset));
    }

    @Override
    public Future<WithdrawHistory> getWithdrawHistory(String asset, BinanceApiCallback<WithdrawHistory> callback) {
        return invoke(callback, () -> client.getWithdrawHistory(asset));
    }

    @Override
    public Future<DepositAddress> getDepositAddress(String asset, BinanceApiCallback<DepositAddress> callback) {
        return invoke(callback, () -> client.getDepositAddress(asset));
    }

    @Override
    public Future<ListenKey> startUserDataStream(BinanceApiCallback<ListenKey> callback) {
        return invoke(callback, () -> {
            client.startUserDataStream();
            return null;
        });
    }

    @Override
    public Future<Void> keepAliveUserDataStream(String listenKey, BinanceApiCallback<Void> callback) {
        return invoke(callback, () -> {
            client.keepAliveUserDataStream(listenKey);
            return null;
        });
    }

    @Override
    public Future<Void> closeUserDataStream(String listenKey, BinanceApiCallback<Void> callback) {
        return invoke(callback, () -> {
            client.closeUserDataStream(listenKey);
            return null;
        });
    }
}
