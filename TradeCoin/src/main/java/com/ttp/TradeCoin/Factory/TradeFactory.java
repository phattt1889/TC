package com.ttp.TradeCoin.Factory;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ttp.TradeCoin.Enums.StockExchangeEnums;
import com.ttp.TradeCoin.entity.User;
import com.ttp.TradeCoin.service.TradeApiService;
import com.ttp.TradeCoin.service.impl.BittrexTradeService;
import com.ttp.TradeCoin.service.impl.PoloniexTradeService;

@Component
public class TradeFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7836264842995444750L;
	
	@Autowired
	private Environment evn;
	
	@Value("${bittrex.private.url}")
	private String url;
	
	public TradeApiService getCurrentStockExchange(User user, StockExchangeEnums stockExchange) {
		
		// return poloniex
		if (StockExchangeEnums.POLONIEX.equals(stockExchange)) {
			return new PoloniexTradeService(user.getTokenCurrency().getKeyApi(),
												user.getTokenCurrency().getKeySecret(),
												"https://poloniex.com/tradingApi?",
												""/*evn.getProperty("poloniex.public.url")*/);
		} else if (StockExchangeEnums.BITTREX.equals(stockExchange)) {
			
			TradeApiService bittrex = new BittrexTradeService(evn.getProperty("bittrex.private.url"), "", "", "");
			return bittrex;
		}
		
		return null;
	}
}
