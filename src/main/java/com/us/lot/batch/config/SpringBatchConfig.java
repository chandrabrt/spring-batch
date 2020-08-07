package com.us.lot.batch.config;

import com.us.lot.batch.batch.UserFieldSetMapper;
import com.us.lot.batch.entity.User;
import com.us.lot.batch.listener.CustomJobListener;
import com.us.lot.batch.utils.PropertyNames;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import static com.us.lot.batch.constant.BatchJobConstant.BATCH_STEP;
import static com.us.lot.batch.constant.BatchJobConstant.ORDER_ITEM_READER;
import static com.us.lot.batch.constant.BatchJobConstant.ORDER_PROCESS_JOB;

/**
 * @author chandra khadka
 * @since 2020-08-06
 */
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig{

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    @Value(PropertyNames.BATCH_SIZE)
    private int size;

    @Value(PropertyNames.CSV_FILE_HEADER)
    private String[] csvFileHeaders;

    @Autowired
    public SpringBatchConfig(StepBuilderFactory stepBuilderFactory,
                             JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job job(ItemReader<User> reader,
                   @Qualifier("customerProcessor") ItemProcessor<User, User> processor,
                   @Qualifier("customDBWriter") ItemWriter<User> writer,
                   @Qualifier("customJobListener") CustomJobListener listener){

        Step step = stepBuilderFactory.get(BATCH_STEP)
                .<User,User>chunk(size)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

        return jobBuilderFactory.get(ORDER_PROCESS_JOB)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step)  // if you have only one step
                //if you have multiple step then
//                .flow(step)
//                .next(step)
                .build();
    }

    @Bean
    public FlatFileItemReader<User> fileItemReader(@Value("${csvFileLocation}") Resource resource){
        return new FlatFileItemReaderBuilder<User>()
                .resource(resource)//Specify path for the resource file to be read.
                .name(ORDER_ITEM_READER)// Name of the ItemReader
                .linesToSkip(1)
                .lineMapper(getLineMapper())//Interface to map lines from file to domain object.
                .strict(false)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
                    setTargetType(User.class);
                }})//Interface to map data obtained from a fieldset to an object.
                .build();

    }

    private LineMapper<User> getLineMapper() {
        final DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        final UserFieldSetMapper fieldSetMapper = new UserFieldSetMapper();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(csvFileHeaders);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
