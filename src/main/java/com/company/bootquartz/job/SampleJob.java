package com.company.bootquartz.job;

import com.company.bootquartz.service.SampleService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SampleJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(SampleJob.class);

    @Autowired
    private SampleService service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {



        LOG.info("Inside SampleJob. I'm running once per 2 seconds.");

        String myValue = (String)jobExecutionContext.getMergedJobDataMap().get("myKey");

        LOG.info("Value under myKey is [{}]", myValue);

        service.printMyValue(myValue);;
    }
}
