[Quarz docs](http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/)
[Quarz job clustering](http://www.quartz-scheduler.org/documentation/quartz-2.1.x/configuration/ConfigJDBCJobStoreClustering)

[SpringBoot + Quartz project](https://github.com/davidkiss/spring-boot-quartz-demo)


# spring-boot-quartz-demo

Sample Spring Boot application that uses the Quartz framework
Based on https://gist.github.com/jelies/5085593 with additional features (liquibase, misfire handling, configurable triggers, enable/disable quartz flag)
Requests are served by AddDynamicJobController.

* To run app
    - Run maven target: mvn spring-boot:run

After application start two jobs that are executing once per 2 and per 5 seconds accordingly started.
* To add new job dynamically call endpoint below (repeat count - is number of repeats. Initial run is not
counted in this number, so if repeat count is 2, job will run 3 times with repeatInterval between runs):
```
     curl -X POST 'http://localhost:9090/jobs/add?startDelay=1&repeatCount=2&repeatInterval=5'
```
* To pause all triggers of scheduler
```
    curl -X POST 'http://localhost:9090/jobs/pause'
```
* To resume all triggers of scheduler
```
    curl -X POST 'http://localhost:9090/jobs/resume'
```