/**
 * 
 */
package com.ttp.TradeCoin.Enums;

/**
 * @author phattt
 *
 */
public enum PoloniexMethodPrivateEnums {

	GET_BALANCES("returnBalances"),
	GET_DEPOSIT_ADDRESS("returnDepositAddresses"),
	GET_GENERATE_ADDRESS("generateNewAddress"),
	GET_HISTORY_DEPOSIT_WITHDRAW("returnDepositsWithdrawals"),
	GET_OPEN_ORDERS("returnOpenOrders"),
	GET_TRADE_HISTORY("returnTradeHistory"),
	GET_BUY("buy"),
	GET_SELL("sell"),
	GET_CANCEL_ORDER("cancelOrder"),
	GET_MOVE_ORDER("moveOrder"),
	GET_WITHDRAW("withdraw"),
	;
	
	private String label;
	
	private PoloniexMethodPrivateEnums(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
}
