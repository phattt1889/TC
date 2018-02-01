/**
 * 
 */
package com.ttp.TradeCoin.Enums;

/**
 * @author phattt
 *
 */
public enum StockExchangeEnums {

	POLONIEX("Poloniex"),
	BITTREX("Bittrex"),
	;
	
	private String label;

	private StockExchangeEnums(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
}
