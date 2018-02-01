/**
 * 
 */
package com.ttp.TradeCoin.service;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * @author phattt
 *
 */
public abstract class ApiCommandResults implements TradeApiService {
	
	protected String keyApi;
	
	protected String keySecret;
	
	protected String urlPrivate;
	
	protected String urlPublic;

	public ApiCommandResults(String keyApi, String keySecret, String urlPrivate, String urlPublic) {
		super();
		this.keyApi = keyApi;
		this.keySecret = keySecret;
		this.urlPrivate = urlPrivate;
		this.urlPublic = urlPublic;
	}

	protected abstract Object returnTradingAPICommandResults(String commandValue, List<NameValuePair> params);
	
	protected abstract Object returnTradingAPICommandResults(String commandValue);
}
