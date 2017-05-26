package com.company.bootquartz.web;

import com.company.bootquartz.job.SampleJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Controller
@RequestMapping("jobs")
public class AddDynamicJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddDynamicJobController.class);

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    /**
     * Adds new job to current scheduler.
     *
     * @param startDelay     period of time that should pass between job scheduling and job start, seconds
     * @param repeatCount    number of times job should be repeated. Initial execution is not counted in this number.
     *                       Example: if repeatCount=2, then job will be executed 3 times
     * @param repeatInterval period of time should pass between job executions, seconds
     * @return TRUE if job scheduled successfully, FALSE otherwise
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Boolean> addNewJob(
            @RequestParam(value = "startDelay", required = false) int startDelay,
            @RequestParam(value = "repeatCount", required = false) int repeatCount,
            @RequestParam(value = "repeatInterval", required = false) int repeatInterval) {

        LOGGER.info(
                "Add new job with startDelay={}, repeatInterval={}, repeatCount={}",
                startDelay, repeatInterval, repeatCount
        );

        // Dynamically pass params to created job
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("myKey", "myValue");

        // Create job itself
        JobDetail job = newJob(SampleJob.class)
                .withIdentity("myJob", "group1")
                .setJobData(jobDataMap)
                .build();

        // Create trigger for job
        Date jobStartDate = new Date(System.currentTimeMillis() + startDelay * 1000);
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startAt(jobStartDate)
                .withSchedule(
                        simpleSchedule()
                                .withIntervalInSeconds(repeatInterval)
                                .withRepeatCount(repeatCount))
                .build();

        try {
            schedulerFactoryBean.getScheduler().scheduleJob(job, trigger);

            LOGGER.info(schedulerFactoryBean.getScheduler().getMetaData().getSummary());
        } catch (SchedulerException e) {
            e.printStackTrace();

            return ResponseEntity.ok().body(Boolean.FALSE);
        }

        return ResponseEntity.ok().body(Boolean.TRUE);
    }

    /**
     * Resume (un-pause) all triggers
     * @return TRUE if all triggers resumed successfully, otherwise FALSE
     */
    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    public ResponseEntity<Boolean> resumeAll() {

        LOGGER.info("Resume all triggers on current scheduler");

        try {
            schedulerFactoryBean.getScheduler().resumeAll();

            LOGGER.info(schedulerFactoryBean.getScheduler().getMetaData().getSummary());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(Boolean.TRUE);
    }

    /**
     * Pause all triggers
     * @return TRUE if all triggers paused successfully, otherwise FALSE
     */
    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    public ResponseEntity<Boolean> pauseAll() {

        LOGGER.info("Resume all triggers on current scheduler");

        try {
            schedulerFactoryBean.getScheduler().pauseAll();

            LOGGER.info(schedulerFactoryBean.getScheduler().getMetaData().getSummary());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(Boolean.TRUE);
    }

}
