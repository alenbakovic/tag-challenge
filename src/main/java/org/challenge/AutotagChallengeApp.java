package org.challenge;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


import org.challenge.configuration.AutotagChallengeConfiguration;
import org.challenge.resource.AutotagChallengeResource;
import org.challenge.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutotagChallengeApp extends Application<AutotagChallengeConfiguration> {

    Logger log = LoggerFactory.getLogger(AutotagChallengeApp.class);

    public static void main(String[] args) throws Exception {
        new AutotagChallengeApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<AutotagChallengeConfiguration> bootstrap) {
    }

    @Override
    public void run(AutotagChallengeConfiguration bookstoreConfiguration, Environment environment) throws Exception {

//        log.info("sleeping for 10 secs...");
//        Thread.sleep(10000);

//        log.info("Configuring postgresql database...");
//        final DBIFactory factory = new DBIFactory();
//        final DBI jdbi = factory.build(environment, bookstoreConfiguration.getDatabase(), "postgresql");
//        final AutotagChallengeDAO bookDAO = jdbi.onDemand(AutotagChallengeDAO.class);

        log.info("Registering resources...");
        TagService tagService = new TagService();
        environment.jersey().register(new AutotagChallengeResource(tagService));
    }
}
