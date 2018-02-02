/**
 * 
 */
package com.ttp.TradeCoin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;

import com.ttp.TradeCoin.Enums.StockExchangeMethodAPIEnums;
import com.ttp.TradeCoin.common.CommonUtils;
import com.ttp.TradeCoin.http.ExecuteApiResults;
import com.ttp.TradeCoin.service.TradeApiService;

/**
 * @author phattt
 *
 */
public class PoloniexTradeService implements TradeApiService {
	
	private String keyApi;
	
	private String keySecret;
	
	@Value("${poloniex.private.url}")
	private String urlPrivate;
	
	@Value("${poloniex.public.url}")
	private String urlPublic;
	
	@Value("${poloniex.fee}")
	private Long fee;
	
	public PoloniexTradeService(String keyApi, String keySecret, String urlPrivate, String urlPublic) {
		super();
		this.keyApi = keyApi;
		this.keySecret = keySecret;
	}

	@Override
	public Object getPublicTradeHistory(String currency, String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getBalances() {
		return returnTradingAPICommandResults(StockExchangeMethodAPIEnums.GET_BALANCES);
	}

	@Override
	public Object getDepositAddresses() {
		return returnTradingAPICommandResults(StockExchangeMethodAPIEnums.GET_DEPOSIT_ADDRESS);
	}

	@Override
	public Object generateNewAddress(String currency) {
		// list param
		List<NameValuePair> params = new ArrayList<>();
		
		// add currency generate
		params.add(new BasicNameValuePair("currency", currency));
		
		return returnTradingAPICommandResults(StockExchangeMethodAPIEnums.GET_GENERATE_ADDRESS, params);
	}

	@Override
	public Object getHistoryDepositsWithdrawals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getOpenOrders(String currencyPair) {
		
		// list param
		List<NameValuePair> params = new ArrayList<>();
		
		// add currency generate
		params.add(new BasicNameValuePair("currencyPair", currencyPair));
		
		return returnTradingAPICommandResults(StockExchangeMethodAPIEnums.GET_OPEN_ORDERS, params);
	}

	@Override
	public Object getTradeHistory(String currencyPair, String start, String end) {
		
		// list param
		List<NameValuePair> params = new ArrayList<>();
		
		// add currency generate
		params.add(new BasicNameValuePair("currencyPair", currencyPair));
		
		// check optional
		if (!CommonUtils.emptyString(start)) {
			params.add(new BasicNameValuePair("start", start));
			
			if (!CommonUtils.emptyString(end)) {
				params.add(new BasicNameValuePair("end", end));
			}
		}
		
		return returnTradingAPICommandResults(StockExchangeMethodAPIEnums.GET_TRADE_HISTORY, params);
	}

	@Override
	public Object exeBuy(String currencyPair, Long rate, Long amount, String immediateOrCancel) {
		// default value is 1
		if (!CommonUtils.emptyString(immediateOrCancel)) {
			immediateOrCancel = "1";
		}
		
		
		// list param
		List<NameValuePair> params = new ArrayList<>();
		
		//get actual rate
		Long actualAmount = amount - (amount * fee);
		
		// add currency generate
		params.add(new BasicNameValuePair("currencyPair", currencyPair));
		params.add(new BasicNameValuePair("rate", rate.toString()));
		params.add(new BasicNameValuePair("amount", actualAmount.toString()));
		params.add(new BasicNameValuePair("immediateOrCancel", immediateOrCancel));
		
		return returnTradingAPICommandResults(StockExchangeMethodAPIEnums.GET_BUY, params);
	}

	@Override
	public Object exeSell(String currencyPair, Long rate, Long amount, String immediateOrCancel) {
		
		// default value is 1
		if (!CommonUtils.emptyString(immediateOrCancel)) {
			immediateOrCancel = "1";
		}
		
		
		// list param
		List<NameValuePair> params = new ArrayList<>();
		
		// add currency generate
		params.add(new BasicNameValuePair("currencyPair", currencyPair));
		params.add(new BasicNameValuePair("rate", rate.toString()));
		params.add(new BasicNameValuePair("amount", amount.toString()));
		params.add(new BasicNameValuePair("immediateOrCancel", immediateOrCancel));
		
		return returnTradingAPICommandResults(StockExchangeMethodAPIEnums.GET_SELL, params);
	}

	@Override
	public Object exeCancelOrder(String orderNumber) {
		List<NameValuePair> params = new ArrayList<>();
		
		// add params
		params.add(new BasicNameValuePair("orderNumber", orderNumber));
		
		return returnTradingAPICommandResults(StockExchangeMethodAPIEnums.GET_CANCEL_ORDER, params);
	}

	@Override
	public Object exeMoveOrder(String orderNumber, Long rate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAvailableAccountBalances(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTradableBalances() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object returnTradingAPICommandResults(StockExchangeMethodAPIEnums commandValue, List<NameValuePair> params) {
		
		// add default post param
		List<NameValuePair> stockExchangePost = new ArrayList<>();
		
		// add value
		stockExchangePost.add(new BasicNameValuePair("nonce", String.valueOf(System.currentTimeMillis())));
		stockExchangePost.add(new BasicNameValuePair("command", commandValue.getPoloniex()));
		
		// add params from variable params
		if (params != null && params.size() > 0) {
			stockExchangePost.addAll(params);
		}
		
		return ExecuteApiResults.returnTradingAPICommandResults(stockExchangePost, urlPrivate, "Key", "Sign",
																								keyApi, keySecret);
	}

	private Object returnTradingAPICommandResults(StockExchangeMethodAPIEnums commandValue) {

		// empty list params
		List<NameValuePair> params = new ArrayList<>();
		
		return returnTradingAPICommandResults(commandValue, params);
	}
}
