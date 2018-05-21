/**
 * Generic interface for Data Access
 * 
 * @author Soumyadeep Mahapatra
 * 
 */

package com.dci.stock.dao;

import java.time.LocalDate;
import java.util.List;

import com.dci.stock.bean.StockQuoteResponse;

public interface StockQuoteDao {
	
	public List<StockQuoteResponse> getClosingPrice(LocalDate fromDate, LocalDate toDate) throws Exception;
	
	public Double getAverageClosingPrice(LocalDate fromDate, LocalDate toDate) throws Exception;
	
}
