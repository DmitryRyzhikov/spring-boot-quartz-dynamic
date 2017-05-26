[Quarz docs](http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/)
[Quarz job clustering](http://www.quartz-scheduler.org/documentation/quartz-2.1.x/configuration/ConfigJDBCJobStoreClustering)

[SpringBoot + Quartz project](https://github.com/davidkiss/spring-boot-quartz-demo)

#TABLE OF CONTENT
* [INTRO](#INTRO)
* [RUN APP](#RUN_APP)
* [ADD JOB](#ADD_JOB)
* [PAUSE TRIGGERS](#PAUSE_TRIGGERS)
* [RESUME TRIGGERS](#RESUME_TRIGGERS)

## INTRO <a name="INTRO"/>

Sample Spring Boot application that uses the Quartz framework
Based on https://gist.github.com/jelies/5085593 with additional features (liquibase, misfire handling, configurable triggers, enable/disable quartz flag)
Requests are served by AddDynamicJobController.

## RUN APP <a name="RUN_APP"/>
    - Run maven target: mvn spring-boot:run

After application start two jobs that are executing once per 2 and per 5 seconds accordingly started.

## ADD JOB <a name="ADD_JOB"/>
To add new job dynamically call endpoint below (repeat count - is number of repeats. Initial run is not
counted in this number, so if repeat count is 2, job will run 3 times with repeatInterval between runs):
```
     curl -X POST 'http://localhost:9090/jobs/add?startDelay=1&repeatCount=2&repeatInterval=5'
```

## PAUSE TRIGGERS <a name="PAUSE_TRIGGERS"/>
To pause all triggers of scheduler
```
    curl -X POST 'http://localhost:9090/jobs/pause'
```

## RESUME TRIGGERS <a name="RESUME_TRIGGERS"/>
To resume all triggers of scheduler
```
    curl -X POST 'http://localhost:9090/jobs/resume'
```