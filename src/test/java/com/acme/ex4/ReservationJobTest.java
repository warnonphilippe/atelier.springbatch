package com.acme.ex4;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringBatchTest
@ActiveProfiles("reservation-job")
class ReservationJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void testStep1() throws Exception {
        JobParameters parameters = jobLauncherTestUtils.getUniqueJobParametersBuilder()
                .toJobParameters();

        // launchJob suppose qu'il n'y ait qu'un seul bean de type Job dans le contexte applicatif.
        JobExecution execution = jobLauncherTestUtils.launchStep("create-file", parameters);
        assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
    }

    @Test
    void testStep2() throws Exception {
        JobParameters parameters = jobLauncherTestUtils.getUniqueJobParametersBuilder()
                .toJobParameters();

        // launchJob suppose qu'il n'y ait qu'un seul bean de type Job dans le contexte applicatif.
        JobExecution execution = jobLauncherTestUtils.launchStep("send-file", parameters);
        assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
    }

    @Test
    @Sql(scripts = {"classpath:org/springframework/batch/core/schema-drop-postgresql.sql", "classpath:org/springframework/batch/core/schema-postgresql.sql"})
    void test() throws Exception {

        JobParameters parameters = jobLauncherTestUtils.getUniqueJobParametersBuilder()
                .toJobParameters();

        // launchJob suppose qu'il n'y ait qu'un seul bean de type Job dans le contexte applicatif.
        JobExecution execution = jobLauncherTestUtils.launchJob(parameters);
        assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
    }

}