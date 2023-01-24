package com.acme.ex4;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
public class Hello2JobConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobLauncher jobLauncher;

    private AtomicBoolean enabled = new AtomicBoolean(false);

    @Scheduled(fixedRate = 3000)
    public void launchJob() throws Exception {
        Date date = new Date();
        JobExecution jobExecution = jobLauncher.run(helloJob2(), new JobParametersBuilder().addDate("launchDate", date)
                .toJobParameters());
    }

    @Bean
    Step stepHello2() {
        var builder = new StepBuilder("stepHello2", this.jobRepository);
        PlatformTransactionManager txManager = new ResourcelessTransactionManager();
        return builder.tasklet((stepContribution, chunkContext) -> {
            System.out.println("-------------------------------------- HELLO FROM 2 -----------------------------------------------");
            return RepeatStatus.FINISHED;
        }, txManager).build();
    }

    @Bean
    Job helloJob2() {
        var builder = new JobBuilder("helloJob2", jobRepository);
        return builder
                // ajout des steps
                .start(stepHello2())
                .build();
    }

}
