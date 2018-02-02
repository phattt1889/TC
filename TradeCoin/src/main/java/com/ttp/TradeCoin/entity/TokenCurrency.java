/**
 * 
 */
package com.ttp.TradeCoin.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ttp.TradeCoin.Enums.StockExchangeEnums;

/**
 * @author phattt
 *
 */

@Entity
@Table(name="token_currency")
public class TokenCurrency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5989025716883329217L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "keyApi")
	private String keyApi;
	
	@Column(name = "keySecret")
	private String keySecret;
	
	@Enumerated(EnumType.STRING)
	private StockExchangeEnums stockExchange;
	
	@Embedded
    private ChangeInfo changeInfo;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the keyApi
	 */
	public String getKeyApi() {
		return keyApi;
	}

	/**
	 * @param keyApi the keyApi to set
	 */
	public void setKeyApi(String keyApi) {
		this.keyApi = keyApi;
	}

	/**
	 * @return the keySecret
	 */
	public String getKeySecret() {
		return keySecret;
	}

	/**
	 * @param keySecret the keySecret to set
	 */
	public void setKeySecret(String keySecret) {
		this.keySecret = keySecret;
	}

	/**
	 * @return the changeInfo
	 */
	public ChangeInfo getChangeInfo() {
		return changeInfo;
	}

	/**
	 * @param changeInfo the changeInfo to set
	 */
	public void setChangeInfo(ChangeInfo changeInfo) {
		this.changeInfo = changeInfo;
	}

	/**
	 * @return the stockExchange
	 */
	public StockExchangeEnums getStockExchange() {
		return stockExchange;
	}

	/**
	 * @param stockExchange the stockExchange to set
	 */
	public void setStockExchange(StockExchangeEnums stockExchange) {
		this.stockExchange = stockExchange;
	}
}
