package com.acme.ex4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("complex-job")
@SpringBatchTest
class ComplexJobTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@Test
	void test() throws Exception {

		JobParameters parameters = jobLauncherTestUtils.getUniqueJobParametersBuilder() 
				.toJobParameters();
		
		// launchJob suppose qu'il n'y ait qu'un seul bean de type Job dans le contexte applicatif.
		JobExecution execution = jobLauncherTestUtils.launchJob(parameters);
		assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
	}
}
