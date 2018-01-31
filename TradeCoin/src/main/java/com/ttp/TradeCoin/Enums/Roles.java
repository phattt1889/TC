/**
 * 
 */
package com.ttp.TradeCoin.Enums;

/**
 * @author phattt
 *
 */
public enum Roles {

	ROLE_BASIC_USER("USER"),
	ROLE_ADMIN("ADMIN"),
	ROLE_ENCHANCED_USER("ENCHANCED USER"),
	;
	
	private String label;

	private Roles(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
}
