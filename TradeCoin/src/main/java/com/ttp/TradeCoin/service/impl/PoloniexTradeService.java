/**
 * 
 */
package com.ttp.TradeCoin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ttp.TradeCoin.Enums.PoloniexMethodPrivateEnums;
import com.ttp.TradeCoin.common.CommonUtils;
import com.ttp.TradeCoin.http.HTTPClient;
import com.ttp.TradeCoin.service.TradeApiService;

/**
 * @author phattt
 *
 */
public class PoloniexTradeService implements TradeApiService {
	
	private String keyApi;
	
	private String keySecret;
	
	private String urlPrivate;
	
	private String urlPublic;
	
	private HTTPClient httpClient;
	
	@Value("${poloniex.fee}")
	private Long fee;
	
	public PoloniexTradeService(String keyApi, String keySecret, String urlPrivate, String urlPublic) {
		super();
		this.keyApi = keyApi;
		this.keySecret = keySecret;
		this.urlPrivate = urlPrivate;
		this.urlPublic = urlPublic;
		
		// init http client
		httpClient = new HTTPClient();
	}

	@Override
	public Object getPublicTradeHistory(String currency, String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getBalances() {
		return returnTradingAPICommandResults(PoloniexMethodPrivateEnums.GET_BALANCES.getLabel());
	}

	@Override
	public Object getDepositAddresses() {
		return returnTradingAPICommandResults(PoloniexMethodPrivateEnums.GET_DEPOSIT_ADDRESS.getLabel());
	}

	@Override
	public Object generateNewAddress(String currency) {
		// list param
		List<NameValuePair> params = new ArrayList<>();
		
		// add currency generate
		params.add(new BasicNameValuePair("currency", currency));
		
		return returnTradingAPICommandResults(PoloniexMethodPrivateEnums.GET_GENERATE_ADDRESS.getLabel(), params);
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
		
		return returnTradingAPICommandResults(PoloniexMethodPrivateEnums.GET_OPEN_ORDERS.getLabel(), params);
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
		
		return returnTradingAPICommandResults(PoloniexMethodPrivateEnums.GET_TRADE_HISTORY.getLabel(), params);
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
		
		return returnTradingAPICommandResults(PoloniexMethodPrivateEnums.GET_BUY.getLabel(), params);
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
		
		return returnTradingAPICommandResults(PoloniexMethodPrivateEnums.GET_SELL.getLabel(), params);
	}

	@Override
	public Object exeCancelOrder(String orderNumber) {
		// TODO Auto-generated method stub
		return null;
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

	private Object returnTradingAPICommandResults(String commandValue, List<NameValuePair> params) {
		
		// add default post param
		List<NameValuePair> poloniexPost = new ArrayList<>();
		
		// add value
		poloniexPost.add(new BasicNameValuePair("nonce", String.valueOf(System.currentTimeMillis())));
		poloniexPost.add(new BasicNameValuePair("command", commandValue));
		
		// add params from variable params
		if (params != null && params.size() > 0) {
			poloniexPost.addAll(params);
		}
		
		// get str request
		StringBuilder buildParam = new StringBuilder();
		for (NameValuePair param : poloniexPost) {
			
			// check string
			if (buildParam.length() > 0) {
				buildParam.append("&");
			}
			
			// add request param
			buildParam.append(param.getName()).append("=").append(param.getValue());
		}
		
		String body = buildParam.toString();
		
		try {
			// data singed
			Mac mac = Mac.getInstance("HmacSHA512");
			mac.init(new SecretKeySpec(keySecret.getBytes(), "HmacSHA512"));
			
			String signature = new String(Hex.encodeHex(mac.doFinal(body.getBytes())));
			
			// add header
			List<NameValuePair> paramHeader = new ArrayList<>();
			paramHeader.add(new BasicNameValuePair("Key", keyApi));
			paramHeader.add(new BasicNameValuePair("Sign", signature));
			
			return httpClient.postHttp(urlPrivate, poloniexPost, paramHeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private Object returnTradingAPICommandResults(String commandValue) {

		// empty list params
		List<NameValuePair> params = new ArrayList<>();
		
		return returnTradingAPICommandResults(commandValue, params);
	}
}
