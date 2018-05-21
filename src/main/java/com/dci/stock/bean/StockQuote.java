/**
 * Bean used for ETL operation to load data from CSV
 * 
 * @author Soumyadeep Mahapatra
 * 
 */

package com.dci.stock.bean;

import java.util.Date;

public class StockQuote {
	
	private Date date;
	private Double openPrice;
	private Double highPrice;
	private Double lowPrice;
	private Double closePrice;
	private Double adjustedClosePrice;
	private long volume;
	
	public StockQuote() {
		
	}
	
	public StockQuote(Date date, Double openPrice, Double highPrice, Double lowPrice, Double closePrice,
			Double adjustedClosePrice, long volume) {
		super();
		this.date = date;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.adjustedClosePrice = adjustedClosePrice;
		this.volume = volume;
	}



	@Override
	public String toString() {
		return "StockQuote [date=" + date + ", openPrice=" + openPrice + ", highPrice=" + highPrice + ", lowPrice="
				+ lowPrice + ", closePrice=" + closePrice + ", adjustedClosePrice=" + adjustedClosePrice + ", volume="
				+ volume + "]";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	public Double getAdjustedClosePrice() {
		return adjustedClosePrice;
	}

	public void setAdjustedClosePrice(Double adjustedClosePrice) {
		this.adjustedClosePrice = adjustedClosePrice;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}
	
	
}
