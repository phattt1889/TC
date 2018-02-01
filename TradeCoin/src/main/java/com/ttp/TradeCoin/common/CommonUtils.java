/**
 * 
 */
package com.ttp.TradeCoin.common;

import java.io.Serializable;

/**
 * @author phattt
 *
 */
public class CommonUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1013075702495685624L;

	/**
	 *check string empty
	 * 
	 * @param str
	 * @return
	 */
	public static boolean emptyString(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		
		return false;
	}
}
