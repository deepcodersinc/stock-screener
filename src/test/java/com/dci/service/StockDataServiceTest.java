package com.dci.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.dci.stock.bean.StockQuoteResponse;
import com.dci.stock.dao.StockQuoteDao;
import com.dci.stock.service.StockDataService;

@RunWith(SpringRunner.class)
public class StockDataServiceTest {

	@TestConfiguration
	static class StockDataServiceTestTestContextConfiguration {

		@Bean
		public StockDataService stockDataService() {
			return new StockDataService();
		}
	}

	@Autowired
	private StockDataService service;

	@MockBean
	private StockQuoteDao dao;

	@Before
	public void setUp() throws Exception {
		Double res = new Double(11);
		Mockito.when(dao.getAverageClosingPrice(LocalDate.now(), LocalDate.now())).thenReturn(res);
		
	}

	@Test
	public void correctYearListTest() throws Exception {
		LocalDate startDate = LocalDate.parse("2005-01-01");
		LocalDate endDate = LocalDate.parse("2008-12-31");
		
		List<StockQuoteResponse> quotes = service.getStockData(startDate, endDate, "yearly");
		assertThat(quotes.size()).isEqualTo(4);
	}

	@Test
	public void correctMonthListTest() throws Exception {
		LocalDate startDate = LocalDate.parse("2005-01-01");
		LocalDate endDate = LocalDate.parse("2005-12-31");
		
		List<StockQuoteResponse> quotes = service.getStockData(startDate, endDate, "monthly");
		assertThat(quotes.size()).isEqualTo(12);
	}
	
	@Test
	public void correctDailyListTest() throws Exception {
		LocalDate startDate = LocalDate.parse("2005-01-01");
		LocalDate endDate = LocalDate.parse("2005-01-31");
		
		Mockito.when(dao.getClosingPrice(startDate, endDate)).thenReturn(createMockResponse(31));
		
		List<StockQuoteResponse> quotes = service.getStockData(startDate, endDate, "daily");
		assertThat(quotes.size()).isEqualTo(31);
	}
	
	private List<StockQuoteResponse> createMockResponse(int num) {
		List<StockQuoteResponse> quotes = new ArrayList<StockQuoteResponse>();
		
		for(int i=0; i<num; i++) {
			quotes.add(new StockQuoteResponse()); 
		}		
		return quotes;
	}
}