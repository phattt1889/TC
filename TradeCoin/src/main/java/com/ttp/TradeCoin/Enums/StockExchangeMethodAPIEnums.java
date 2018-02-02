/**
 * 
 */
package com.ttp.TradeCoin.Enums;

/**
 * @author phattt
 *
 */
public enum StockExchangeMethodAPIEnums {

	GET_BALANCES("returnBalances", "/account/getbalances"),
	GET_BALANCE("", "/account/getbalance"),
	GET_DEPOSIT_ADDRESS("returnDepositAddresses", ""),
	GET_GENERATE_ADDRESS("generateNewAddress", ""),
	GET_HISTORY_DEPOSIT_WITHDRAW("returnDepositsWithdrawals", ""),
	GET_OPEN_ORDERS("returnOpenOrders", ""),
	GET_TRADE_HISTORY("returnTradeHistory", ""),
	GET_BUY("buy", ""),
	GET_SELL("sell", ""),
	GET_CANCEL_ORDER("cancelOrder", ""),
	GET_MOVE_ORDER("moveOrder", ""),
	GET_WITHDRAW("withdraw", ""),
	;
	
	private String poloniex;
	
	private String bittrex;

	private StockExchangeMethodAPIEnums(String poloniex, String bittrex) {
		this.poloniex = poloniex;
		this.bittrex = bittrex;
	}

	/**
	 * @return the poloniex
	 */
	public String getPoloniex() {
		return poloniex;
	}

	/**
	 * @return the bittrex
	 */
	public String getBittrex() {
		return bittrex;
	}
}
