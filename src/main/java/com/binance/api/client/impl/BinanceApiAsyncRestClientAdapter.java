package com.binance.api.client.impl;

import com.binance.api.client.BinanceApiAsyncExecutorRestClient;
import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.*;
import com.binance.api.client.domain.event.ListenKey;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.ServerTime;
import com.binance.api.client.domain.market.*;

import java.util.List;

/**
 * Adapter to make calls to a BinanceApiAsyncExecutorRestClient using the BinanceApiAsyncRestClient
 * interface.  This allows existing code to migrate to use BinanceApiAsyncExecutorRestClient with dedicated
 * ExecutorServices without a major lift.
 * If you are writing new code, consider to use BinanceApiAsyncExecutorRestClient directly.
 */
public class BinanceApiAsyncRestClientAdapter implements BinanceApiAsyncRestClient {
    private BinanceApiAsyncExecutorRestClient delegate;

    public BinanceApiAsyncRestClientAdapter(BinanceApiAsyncExecutorRestClient delegate) {
        this.delegate = delegate;
    }

    @Override
    public void ping(BinanceApiCallback<Void> callback) {
        delegate.ping(callback);
    }

    @Override
    public void getServerTime(BinanceApiCallback<ServerTime> callback) {
        delegate.getServerTime(callback);
    }

    @Override
    public void getExchangeInfo(BinanceApiCallback<ExchangeInfo> callback) {
        delegate.getExchangeInfo(callback);
    }

    @Override
    public void getAllAssets(BinanceApiCallback<List<Asset>> callback) {
        delegate.getAllAssets(callback);
    }

    @Override
    public void getOrderBook(String symbol, Integer limit, BinanceApiCallback<OrderBook> callback) {
        delegate.getOrderBook(symbol, limit,callback);
    }

    @Override
    public void getTrades(String symbol, Integer limit, BinanceApiCallback<List<TradeHistoryItem>> callback) {
        delegate.getTrades(symbol, limit,callback);
    }

    @Override
    public void getHistoricalTrades(String symbol, Integer limit, Long fromId, BinanceApiCallback<List<TradeHistoryItem>> callback) {
        delegate.getHistoricalTrades(symbol, limit, fromId,callback);
    }

    @Override
    public void getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<AggTrade>> callback) {
        delegate.getAggTrades(symbol, fromId,limit, startTime, endTime, callback);
    }

    @Override
    public void getAggTrades(String symbol, BinanceApiCallback<List<AggTrade>> callback) {
        delegate.getAggTrades(symbol, callback);
    }

    @Override
    public void getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<Candlestick>> callback) {
        delegate.getCandlestickBars(symbol, interval, limit, startTime, endTime,callback);
    }

    @Override
    public void getCandlestickBars(String symbol, CandlestickInterval interval, BinanceApiCallback<List<Candlestick>> callback) {
        delegate.getCandlestickBars(symbol, interval,callback);
    }

    @Override
    public void get24HrPriceStatistics(String symbol, BinanceApiCallback<TickerStatistics> callback) {
        delegate.get24HrPriceStatistics(symbol,callback);
    }

    @Override
    public void getAll24HrPriceStatistics(BinanceApiCallback<List<TickerStatistics>> callback) {
        delegate.getAll24HrPriceStatistics(callback);
    }

    @Override
    public void getAllPrices(BinanceApiCallback<List<TickerPrice>> callback) {
        delegate.getAllPrices(callback);
    }

    @Override
    public void getPrice(String symbol, BinanceApiCallback<TickerPrice> callback) {
        delegate.getPrice(symbol,callback);
    }

    @Override
    public void getBookTickers(BinanceApiCallback<List<BookTicker>> callback) {
        delegate.getBookTickers(callback);
    }

    @Override
    public void newOrder(NewOrder order, BinanceApiCallback<NewOrderResponse> callback) {
        delegate.newOrder(order,callback);
    }

    @Override
    public void newOrderTest(NewOrder order, BinanceApiCallback<Void> callback) {
        delegate.newOrderTest(order,callback);
    }

    @Override
    public void getOrderStatus(OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback) {
        delegate.getOrderStatus(orderStatusRequest,callback);
    }

    @Override
    public void cancelOrder(CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback) {
        delegate.cancelOrder(cancelOrderRequest,callback);
    }

    @Override
    public void getOpenOrders(OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
        delegate.getOpenOrders(orderRequest,callback);
    }

    @Override
    public void getAllOrders(AllOrdersRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
        delegate.getAllOrders(orderRequest,callback);
    }

    @Override
    public void getAccount(Long recvWindow, Long timestamp, BinanceApiCallback<Account> callback) {
        delegate.getAccount(recvWindow,timestamp,callback);
    }

    @Override
    public void getAccount(BinanceApiCallback<Account> callback) {
        delegate.getAccount(callback);
    }

    @Override
    public void getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp, BinanceApiCallback<List<Trade>> callback) {
        delegate.getMyTrades(symbol, limit,fromId, recvWindow, timestamp,callback);
    }

    @Override
    public void getMyTrades(String symbol, Integer limit, BinanceApiCallback<List<Trade>> callback) {
        delegate.getMyTrades(symbol, limit,callback);
    }

    @Override
    public void getMyTrades(String symbol, BinanceApiCallback<List<Trade>> callback) {
        delegate.getMyTrades(symbol, callback);
    }

    @Override
    public void withdraw(String asset, String address, String amount, String name, String addressTag, BinanceApiCallback<WithdrawResult> callback) {
        delegate.withdraw(asset, address,amount, name, addressTag,callback);
    }

    @Override
    public void getDepositHistory(String asset, BinanceApiCallback<DepositHistory> callback) {
        delegate.getDepositHistory(asset, callback);
    }

    @Override
    public void getWithdrawHistory(String asset, BinanceApiCallback<WithdrawHistory> callback) {
        delegate.getWithdrawHistory(asset, callback);
    }

    @Override
    public void getDepositAddress(String asset, BinanceApiCallback<DepositAddress> callback) {
        delegate.getDepositAddress(asset, callback);
    }

    @Override
    public void startUserDataStream(BinanceApiCallback<ListenKey> callback) {
        delegate.startUserDataStream(callback);
    }

    @Override
    public void keepAliveUserDataStream(String listenKey, BinanceApiCallback<Void> callback) {
        delegate.keepAliveUserDataStream(listenKey, callback);
    }

    @Override
    public void closeUserDataStream(String listenKey, BinanceApiCallback<Void> callback) {
        delegate.closeUserDataStream(listenKey, callback);
    }
}
