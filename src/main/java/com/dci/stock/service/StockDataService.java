/**
 * Business logic Service to fetch stock data
 * 
 * @author Soumyadeep Mahapatra
 * 
 */
package com.dci.stock.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dci.stock.bean.Constants;
import com.dci.stock.bean.StockQuoteResponse;
import com.dci.stock.dao.StockQuoteDao;
import com.dci.stock.exception.InvalidInputException;

@Service
public class StockDataService {

	protected final Logger logger = LoggerFactory.getLogger(StockDataService.class);
	List<StockQuoteResponse> responseList = new ArrayList<StockQuoteResponse>();
	StockQuoteResponse quoteResponse = null;
	
	@Autowired
	private StockQuoteDao stockQuoteDao;
	
	/**
	 * Takes in requests and gets data appropriately depending on the date range. <code>interval</code> marked as 'daily'
	 * get the actual closePrice and those marked with 'monthly' or 'yearly', the average is computed
	 *  
	 * 
	 * @param startDate
	 * @param endDate
	 * @param interval
	 * @return List<StockQuoteResponse>
	 * @throws Exception
	 */
	public List<StockQuoteResponse> getStockData(LocalDate startDate, LocalDate endDate, String interval) throws Exception {
		
		responseList = new ArrayList<StockQuoteResponse>();
		LocalDate startCursor = null;
		LocalDate endCursor = null;
		
		if(startDate.isAfter(endDate)) {
			throw new InvalidInputException("startDate is after endDate", HttpStatus.BAD_REQUEST);
		}
		
		switch(interval) {
		case Constants.DAILY:			
			responseList = stockQuoteDao.getClosingPrice(startDate, endDate);
			return responseList;
		
		case Constants.MONTHLY:			
			startCursor = startDate;
			endCursor = startCursor.plusDays(startCursor.lengthOfMonth()-1);
					
			while(endCursor.isBefore(endDate) || endCursor.isEqual(endDate)) {	
				Double closePrice = stockQuoteDao.getAverageClosingPrice(startCursor, endCursor);
				
				if(closePrice != null) {
					responseList.add(new StockQuoteResponse(null, startCursor.getMonthValue(), startCursor.getYear(), closePrice));
				}	
				startCursor = startCursor.plusMonths(1);
				endCursor = startCursor.plusDays(startCursor.lengthOfMonth()-1);			
			}						
			return responseList;	
			
		case Constants.YEARLY:			
			startCursor = startDate;
			endCursor = startCursor.plusDays(startCursor.lengthOfYear()-1);
					
			while(endCursor.getYear() <= endDate.getYear()) {
				Double closePrice = stockQuoteDao.getAverageClosingPrice(startCursor, endCursor);
				
				if(closePrice != null) {
					responseList.add(new StockQuoteResponse(null, null, startCursor.getYear(), closePrice));
				}
				startCursor = startCursor.plusYears(1);
				endCursor = startCursor.plusDays(startCursor.lengthOfYear()-1);			
			}	
			return responseList;
		}
		
		return responseList;		
	}	
}
