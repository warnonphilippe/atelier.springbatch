package com.acme.ex4;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBatchTest
@ActiveProfiles("test")
class Hello1JobConfigTest {

    @Autowired
    private JobLauncherTestUtils launcher;

    @Test
    @Sql(scripts = {"classpath:org/springframework/batch/core/schema-drop-postgresql.sql", "classpath:org/springframework/batch/core/schema-postgresql.sql"})
    void testJob() throws Exception {

        JobParameters params = launcher.getUniqueJobParameters();
        JobExecution exec = launcher.launchJob(params);
        assertEquals(BatchStatus.COMPLETED, exec.getStatus());

    }
}