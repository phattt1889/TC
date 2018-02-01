package com.ttp.TradeCoin.Factory;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ttp.TradeCoin.Enums.StockExchangeEnums;
import com.ttp.TradeCoin.entity.User;
import com.ttp.TradeCoin.service.TradeApiService;
import com.ttp.TradeCoin.service.impl.PoloniexTradeService;

public class TradeFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7836264842995444750L;
	
	private StockExchangeEnums stockExchange;
	
	@Autowired
	private Environment evn;

	public TradeFactory(StockExchangeEnums stockExchange) {
		super();
		this.stockExchange = stockExchange;
	}
	
	public TradeApiService getCurrentStockExchange(User user) {
		
		// return poloniex
		if (StockExchangeEnums.POLONIEX.equals(stockExchange)) {
			return new PoloniexTradeService(user.getTokenCurrency().getKeyApi(),
												user.getTokenCurrency().getKeySecret(),
												"https://poloniex.com/tradingApi?",
												""/*evn.getProperty("poloniex.public.url")*/);
		}
		
		return null;
	}

}
