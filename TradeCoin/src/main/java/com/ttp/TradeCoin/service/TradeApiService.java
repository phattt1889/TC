package com.ttp.TradeCoin.service;

public interface TradeApiService {

	/**
	 * public API
	 */
	public Object getPublicTradeHistory(String currency, String start, String end);
	
	/**
	 * private API
	 */
	
	public Object getBalances();
	
	public Object getDepositAddresses();
	
	public Object generateNewAddress(String currency);
	
	public Object getHistoryDepositsWithdrawals();
	
	public Object getOpenOrders(String currencyPair);
	
	public Object getTradeHistory(String currencyPair, String start, String end);
	
	public Object exeBuy(String currencyPair, Long rate, Long amount, String immediateOrCancel);
	
	public Object exeSell(String currencyPair, Long rate, Long amount, String immediateOrCancel);
	
	public Object exeCancelOrder(String orderNumber);
	
	public Object exeMoveOrder(String orderNumber, Long rate);
	
	public Object getAvailableAccountBalances(String account);
	
	public Object getTradableBalances();
}
