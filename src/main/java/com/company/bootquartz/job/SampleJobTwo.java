package com.company.bootquartz.job;

import com.company.bootquartz.service.SampleService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SampleJobTwo implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(SampleJobTwo.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOG.info("Inside SampleJobTwo I'm running once per 5 seconds.");
    }
}
