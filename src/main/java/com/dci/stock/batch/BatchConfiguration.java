package com.dci.stock.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import com.dci.stock.bean.StockQuote;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:data-source.properties")
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Value("${csv.filename}")
    private String fileName;

    @Bean
    public FlatFileItemReader<StockQuote> reader() {
        return new FlatFileItemReaderBuilder<StockQuote>()
            .name("quoteItemReader")
            .resource(new ClassPathResource(fileName))
            .linesToSkip(1)
            .delimited()
            .names(new String[]{"date", "openPrice", "highPrice", "lowPrice", "closePrice", "adjustedClosePrice", "volume"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<StockQuote>() {{
                setTargetType(StockQuote.class);
            }})
            .build();
    }

    @Bean
    public JdbcBatchItemWriter<StockQuote> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<StockQuote>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO stock_quote (date, open, high, low, close, adjusted_close, volume) "
            		+ "VALUES (:date, :openPrice, :highPrice, :lowPrice, :closePrice, :adjustedClosePrice, :volume)")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<StockQuote> writer) {
        return stepBuilderFactory.get("step1")
            .<StockQuote, StockQuote> chunk(5)
            .reader(reader())
            .writer(writer)
            .build();
    }
}