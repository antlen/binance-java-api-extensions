package com.binance.api.client;

import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.*;
import com.binance.api.client.domain.event.ListenKey;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.ServerTime;
import com.binance.api.client.domain.market.*;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Binance API facade, supporting asynchronous/non-blocking access Binance's REST API using ExecutorServices to
 * handling the request and response threading.
 */
public interface BinanceApiAsyncExecutorRestClient {

  // General endpoints

  /**
   * Test connectivity to the Rest API.
   * @return
   */
  Future<Void> ping(BinanceApiCallback<Void> callback);

  /**
   * Check server time.
   * @return
   */
  Future<ServerTime> getServerTime(BinanceApiCallback<ServerTime> callback);

  /**
   * Current exchange trading rules and symbol information
   *
   * @return
   */
  Future<ExchangeInfo> getExchangeInfo(BinanceApiCallback<ExchangeInfo> callback);

  /**
   * ALL supported assets and whether or not they can be withdrawn.
   *
   * @return
   */
  Future<List<Asset>> getAllAssets(BinanceApiCallback<List<Asset>> callback);

  // Market Data endpoints

  /**
   * Get order book of a symbol (asynchronous)
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param limit depth of the order book (max 100)
   * @param callback the callback that handles the response
   *
   * @return
   */
  Future<OrderBook> getOrderBook(String symbol, Integer limit, BinanceApiCallback<OrderBook> callback);

  /**
   * Get recent trades (up to last 500). Weight: 1
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param limit of last trades (Default 500; max 1000.)
   * @param callback the callback that handles the response
   *
   * @return
   */
  Future<List<TradeHistoryItem>> getTrades(String symbol, Integer limit, BinanceApiCallback<List<TradeHistoryItem>> callback);

  /**
   * Get older trades. Weight: 5
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param limit of last trades (Default 500; max 1000.)
   * @param fromId TradeId to fetch from. Default gets most recent trades.
   * @param callback the callback that handles the response
   *
   * @return
   */
  Future<List<TradeHistoryItem>> getHistoricalTrades(String symbol, Integer limit, Long fromId, BinanceApiCallback<List<TradeHistoryItem>> callback);

  /**
   * Get compressed, aggregate trades. Trades that fill at the time, from the same order, with
   * the same price will have the quantity aggregated.
   *
   * If both <code>startTime</code> and <code>endTime</code> are sent, <code>limit</code>should not
   * be sent AND the distance between <code>startTime</code> and <code>endTime</code> must be less than 24 hours.
   *
   * @param symbol symbol to aggregate (mandatory)
   * @param fromId ID to get aggregate trades from INCLUSIVE (optional)
   * @param limit Default 500; max 1000 (optional)
   * @param startTime Timestamp in ms to get aggregate trades from INCLUSIVE (optional).
   * @param endTime Timestamp in ms to get aggregate trades until INCLUSIVE (optional).
   * @param callback the callback that handles the response
   * @return a list of aggregate trades for the given symbol
   */
  Future<List<AggTrade>> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<AggTrade>> callback);

  /**
   * Return the most recent aggregate trades for <code>symbol</code>
   *
   * @see #getAggTrades(String, String, Integer, Long, Long, BinanceApiCallback)
   */
  Future<List<AggTrade>> getAggTrades(String symbol, BinanceApiCallback<List<AggTrade>> callback);

  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
   *
   * @param symbol symbol to aggregate (mandatory)
   * @param interval candlestick interval (mandatory)
   * @param limit Default 500; max 1000 (optional)
   * @param startTime Timestamp in ms to get candlestick bars from INCLUSIVE (optional).
   * @param endTime Timestamp in ms to get candlestick bars until INCLUSIVE (optional).
   * @param callback the callback that handles the response containing a candlestick bar for the given symbol and interval
   */
  Future<List<Candlestick>> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<Candlestick>> callback);

  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
   *
   * @see #getCandlestickBars(String, CandlestickInterval, BinanceApiCallback)
   */
  Future<List<Candlestick>> getCandlestickBars(String symbol, CandlestickInterval interval, BinanceApiCallback<List<Candlestick>> callback);

  /**
   * Get 24 hour price change statistics (asynchronous).
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param callback the callback that handles the response
   */
  Future<TickerStatistics> get24HrPriceStatistics(String symbol, BinanceApiCallback<TickerStatistics> callback);
  
  /**
   * Get 24 hour price change statistics for all symbols (asynchronous).
   * 
   * @param callback the callback that handles the response
   */
  Future<List<TickerStatistics>> getAll24HrPriceStatistics(BinanceApiCallback<List<TickerStatistics>> callback);

  /**
   * Get Latest price for all symbols (asynchronous).
   *
   * @param callback the callback that handles the response
   */
  Future<List<TickerPrice>> getAllPrices(BinanceApiCallback<List<TickerPrice>> callback);
  
  /**
   * Get latest price for <code>symbol</code> (asynchronous).
   * 
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param callback the callback that handles the response
   */
  Future<TickerPrice> getPrice(String symbol , BinanceApiCallback<TickerPrice> callback);

  /**
   * Get best price/qty on the order book for all symbols (asynchronous).
   *
   * @param callback the callback that handles the response
   */
  Future<List<BookTicker>> getBookTickers(BinanceApiCallback<List<BookTicker>> callback);

  // Account endpoints

  /**
   * Send in a new order (asynchronous)
   *
   * @param order the new order to submit.
   * @param callback the callback that handles the response
   */
  Future<NewOrderResponse> newOrder(NewOrder order, BinanceApiCallback<NewOrderResponse> callback);

  /**
   * Test new order creation and signature/recvWindow long. Creates and validates a new order but does not send it into the matching engine.
   *
   * @param order the new TEST order to submit.
   * @param callback the callback that handles the response
   */
  Future<Void> newOrderTest(NewOrder order, BinanceApiCallback<Void> callback);

  /**
   * Check an order's status (asynchronous).
   *
   * @param orderStatusRequest order status request parameters
   * @param callback the callback that handles the response
   */
  Future<Order> getOrderStatus(OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback);

  /**
   * Cancel an active order (asynchronous).
   *
   * @param cancelOrderRequest order status request parameters
   * @param callback the callback that handles the response
   */
  Future<CancelOrderResponse> cancelOrder(CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback);

  /**
   * Get all open orders on a symbol (asynchronous).
   *
   * @param orderRequest order request parameters
   * @param callback the callback that handles the response
   */
  Future<List<Order>> getOpenOrders(OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback);

  /**
   * Get all account orders; active, canceled, or filled.
   *
   * @param orderRequest order request parameters
   * @param callback the callback that handles the response
   */
  Future<List<Order>> getAllOrders(AllOrdersRequest orderRequest, BinanceApiCallback<List<Order>> callback);

  /**
   * Get current account information (async).
   */
  Future<Account> getAccount(Long recvWindow, Long timestamp, BinanceApiCallback<Account> callback);

  /**
   * Get current account information using default parameters (async).
   */
  Future<Account> getAccount(BinanceApiCallback<Account> callback);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol symbol to get trades from
   * @param limit default 500; max 1000
   * @param fromId TradeId to fetch from. Default gets most recent trades.
   * @param callback the callback that handles the response with a list of trades
   */
  Future<List<Trade>> getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp, BinanceApiCallback<List<Trade>> callback);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol symbol to get trades from
   * @param limit default 500; max 1000
   * @param callback the callback that handles the response with a list of trades
   */
  Future<List<Trade>> getMyTrades(String symbol, Integer limit, BinanceApiCallback<List<Trade>> callback);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol symbol to get trades from
   * @param callback the callback that handles the response with a list of trades
   */
  Future<List<Trade>> getMyTrades(String symbol, BinanceApiCallback<List<Trade>> callback);

  /**
   * Submit a withdraw request.
   *
   * Enable Withdrawals option has to be active in the API settings.
   *
   * @param asset asset symbol to withdraw
   * @param address address to withdraw to
   * @param amount amount to withdraw
   * @param name description/alias of the address
   * @param addressTag Secondary address identifier for coins like XRP,XMR etc.
   */
  Future<WithdrawResult> withdraw(String asset, String address, String amount, String name, String addressTag, BinanceApiCallback<WithdrawResult> callback);

  /**
   * Fetch account deposit history.
   *
   * @param callback the callback that handles the response and returns the deposit history
   */
  Future<DepositHistory> getDepositHistory(String asset, BinanceApiCallback<DepositHistory> callback);

  /**
   * Fetch account withdraw history.
   *
   * @param callback the callback that handles the response and returns the withdraw history
   */
  Future<WithdrawHistory> getWithdrawHistory(String asset, BinanceApiCallback<WithdrawHistory> callback);

  /**
   * Fetch deposit address.
   *
   * @param callback the callback that handles the response and returns the deposit address
   */
  Future<DepositAddress> getDepositAddress(String asset, BinanceApiCallback<DepositAddress> callback);

  // User stream endpoints

  /**
   * Start a new user data stream.
   *
   * @param callback the callback that handles the response which contains a listenKey
   */
  Future<ListenKey> startUserDataStream(BinanceApiCallback<ListenKey> callback);

  /**
   * PING a user data stream to prevent a time out.
   *
   * @param listenKey listen key that identifies a data stream
   * @param callback the callback that handles the response which contains a listenKey
   */
  Future<Void> keepAliveUserDataStream(String listenKey, BinanceApiCallback<Void> callback);

  /**
   * Close out a new user data stream.
   *
   * @param listenKey listen key that identifies a data stream
   * @param callback the callback that handles the response which contains a listenKey
   */
  Future<Void> closeUserDataStream(String listenKey, BinanceApiCallback<Void> callback);
}