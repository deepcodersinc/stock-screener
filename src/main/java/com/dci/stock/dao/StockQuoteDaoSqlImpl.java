/**
 * SQL implementation for Data Access
 * 
 * @author Soumyadeep Mahapatra
 * 
 */
package com.dci.stock.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dci.stock.bean.StockQuoteResponse;
import com.dci.stock.exception.InvalidInputException;

@Service
public class StockQuoteDaoSqlImpl implements StockQuoteDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public StockQuoteDaoSqlImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<StockQuoteResponse> getClosingPrice(LocalDate fromDate, LocalDate toDate) throws Exception {
		List<StockQuoteResponse> quotes = null;
		
		try {
			quotes = 
					jdbcTemplate.query("SELECT date, close FROM stock_quote"
							+ " WHERE date >= '" + fromDate.toString() + "'"
							+ " AND date <= '" + toDate.toString() + "'",
							(rs, row) -> new StockQuoteResponse(
									rs.getDate(1).toLocalDate(),
									rs.getDouble(2)));
		} catch (Exception e) {
			throw e;
		}  
		
		return quotes;		
	}
	
	public Double getAverageClosingPrice(LocalDate fromDate, LocalDate toDate) throws Exception {
		List<Double> quotes = null;
		
		try {
			quotes = 
				jdbcTemplate.query("SELECT close FROM stock_quote"
						+ " WHERE date >= '" + fromDate.toString() + "'"
						+ " AND date <= '" + toDate.toString() + "'",
						(rs, row) -> new Double(rs.getDouble(1)));
		} catch (Exception e) { //TODO Change to SQL specific exception
			throw new InvalidInputException("Unknown error occured", HttpStatus.NOT_IMPLEMENTED);
		} 
		
		if(quotes == null || quotes.isEmpty()) {
			return null;
		}
							
		double sum = 0.0;
		
		for(Double quote: quotes) {
			sum = sum + quote;
		}		
		return sum / quotes.size();		
	}
}

