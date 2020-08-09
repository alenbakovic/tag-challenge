package org.challenge;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


import org.challenge.configuration.AutotagChallengeConfiguration;
import org.challenge.dao.AutotagChallengeDAO;
import org.challenge.resource.AutotagChallengeResource;
import org.challenge.service.TagService;
import org.skife.jdbi.v2.DBI;
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
    public void run(AutotagChallengeConfiguration autotagChallengeConfiguration, Environment environment) throws Exception {

        // TODO: Improve this, now this waits unit DB is up and running
        log.info("sleeping for 30 secs...");
        Thread.sleep(30000);

        log.info("Configuring postgresql database...");
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, autotagChallengeConfiguration.getDatabase(), "postgresql");
        final AutotagChallengeDAO autotagChallengeDAO = jdbi.onDemand(AutotagChallengeDAO.class);

        log.info("Registering resources...");
        TagService tagService = new TagService();
        environment.jersey().register(new AutotagChallengeResource(autotagChallengeDAO, tagService));
    }
}
