/**
 * Rest controller class to fetch the stock data
 * 
 * @author Soumyadeep Mahapatra
 * 
 */

package com.dci.stock.api;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dci.stock.bean.Constants;
import com.dci.stock.bean.StockQuoteResponse;
import com.dci.stock.exception.InvalidInputException;
import com.dci.stock.service.StockDataService;

@RestController
@RequestMapping("/api/")
public class RestAPIDataController {

	private final Logger logger = LoggerFactory.getLogger(RestAPIDataController.class);
	private LocalDate startDate = LocalDate.now();
	private LocalDate endDate = LocalDate.now();
	
	@Autowired
	StockDataService dataService;

	/**
	 * Returns a list of close prices for a stock within a date range
	 *  
	 * @param symbol
	 * @param startDate
	 * @param endDate
	 * @return List<StockQuoteResponse>
	 * @throws Exception
	 */
	@GetMapping(value = "/data/{symbol}/close")
	public List<StockQuoteResponse> getHistoricalStockData(@PathVariable String symbol, 
			@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate) throws Exception {
		
		return dataService.getStockData(startDate, endDate, Constants.DAILY);
	}
	
	/**
	 * Returns all historical stock close prices averaged annually 
	 * 
	 * @param symbol
	 * @return List<StockQuoteResponse>
	 * @throws Exception
	 */
	@GetMapping(value = "/data/{symbol}/avgclose")
	public List<StockQuoteResponse> getHistoricalStockData(@PathVariable String symbol) throws Exception {
		startDate = LocalDate.of(1972, 6, 1);
		endDate = LocalDate.now();
		
		return dataService.getStockData(startDate, endDate, Constants.YEARLY);
	}
	
	/**
	 * Returns all historical stock close prices averaged monthly for a given year  
	 * 
	 * @param symbol
	 * @param year
	 * @return List<StockQuoteResponse>
	 * @throws Exception
	 */
	@GetMapping(value = "/data/{symbol}/avgclose/year/{year}")
	public List<StockQuoteResponse> getHistoricalStockData(@PathVariable String symbol, 
			@PathVariable String year) throws Exception {
		
		try {
			startDate = LocalDate.of(Integer.valueOf(year), 1, 1);
			endDate = LocalDate.of(Integer.valueOf(year), 12, 31);
		} catch (InvalidInputException ine) {
			throw new InvalidInputException("Invalid year", HttpStatus.BAD_REQUEST);
		}
		
		if(startDate.isAfter(LocalDate.now())) {
			throw new InvalidInputException("Invalid year", HttpStatus.BAD_REQUEST);
		}
		
		return dataService.getStockData(startDate, endDate, Constants.MONTHLY);
	}
	
	/**
	 * Returns all historical stock close prices for every day for a given month and year
	 * 
	 * @param symbol
	 * @param year
	 * @param month
	 * @return List<StockQuoteResponse>
	 * @throws Exception
	 */
	@GetMapping(value = "/data/{symbol}/avgclose/year/{year}/month/{month}")
	public List<StockQuoteResponse> getHistoricalStockData(@PathVariable String symbol, 
			@PathVariable String year, @PathVariable String month) throws Exception {
		
		try {
			startDate = LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), 1);		
			endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
		} catch (InvalidInputException ine) {
			throw new InvalidInputException("Invalid year or month", HttpStatus.BAD_REQUEST);
		}	
		
		if(startDate.isAfter(LocalDate.now())) {
			throw new InvalidInputException("Invalid year or month", HttpStatus.BAD_REQUEST);
		}
		
		return dataService.getStockData(startDate, endDate, Constants.DAILY);
	}
}
