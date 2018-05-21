/**
 * Bean class used for transforming to JSON/XML response
 * 
 * @author Soumyadeep Mahapatra
 * 
 */
package com.dci.stock.bean;

import java.time.LocalDate;

public class StockQuoteResponse {
	
	private Integer dayOfMonth;
	private Integer monthOfYear;
	private Integer year;
	private Double closePrice;
	
	public StockQuoteResponse() {
		
	}

	public StockQuoteResponse(Integer dayOfMonth, Integer monthOfYear, Integer year, Double closePrice) {
		super();
		this.dayOfMonth = dayOfMonth;
		this.monthOfYear = monthOfYear;
		this.year = year;
		this.closePrice = closePrice;
	}
	
	public StockQuoteResponse(LocalDate date, Double closePrice) {
		super();
		this.dayOfMonth = date.getDayOfMonth();
		this.monthOfYear = date.getMonthValue();
		this.year = date.getYear();
		this.closePrice = closePrice;
	}

	public Integer getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public Integer getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(Integer monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}
	
	
}
