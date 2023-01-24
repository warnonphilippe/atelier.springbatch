package com.acme.ex4;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("complex-job")
public class ComplexJob {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    Step a() {
        StepBuilder stepBuilder = new StepBuilder("a", this.jobRepository);
        return stepBuilder.tasklet((stepContribution, chunkContext) -> {
            System.out.println("a");
            stepContribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }, this.transactionManager).build();
    }

    @Bean
    Step aFallback() {
        StepBuilder stepBuilder = new StepBuilder("aFallback", this.jobRepository);

        return stepBuilder.tasklet((stepContribution, chunkContext) -> {
            System.out.println("a fallback");
            stepContribution.setExitStatus(ExitStatus.COMPLETED);
            return RepeatStatus.FINISHED;
        }, this.transactionManager).build();
    }

    @Bean
    Step aFallbackNext() {
        StepBuilder stepBuilder = new StepBuilder("aFallbackNext", this.jobRepository);

        return stepBuilder.tasklet((stepContribution, chunkContext) -> {
            System.out.println("a fallback next");
            stepContribution.setExitStatus(ExitStatus.COMPLETED);
            return RepeatStatus.FINISHED;
        }, this.transactionManager).build();
    }

    @Bean
    Step b() {
        StepBuilder stepBuilder = new StepBuilder("b", this.jobRepository);

        return stepBuilder.tasklet((stepContribution, chunkContext) -> {
            System.out.println("b");
            stepContribution.setExitStatus(ExitStatus.COMPLETED);
            return RepeatStatus.FINISHED;
        }, this.transactionManager).build();
    }
    
    @Bean
    Step c() {
        StepBuilder stepBuilder = new StepBuilder("c", this.jobRepository);

        return stepBuilder.tasklet((stepContribution, chunkContext) -> {
            System.out.println("c");
            stepContribution.setExitStatus(ExitStatus.COMPLETED);
            return RepeatStatus.FINISHED;
        }, this.transactionManager).build();
    }

    @Bean
    public Job myJob() {
        JobBuilder builder = new JobBuilder("myJob", this.jobRepository);
        return builder
                .flow(a())
                    .on(ExitStatus.FAILED.getExitCode())
                        .to(aFallback())
                        .next(aFallbackNext())
                .from(a())
                    .on(ExitStatus.COMPLETED.getExitCode())
                        .to(b())
                        .next(c())
                .from(aFallbackNext())
                    .next(b())
                    .next(c())
                    .end()
                .build();

    }
}
