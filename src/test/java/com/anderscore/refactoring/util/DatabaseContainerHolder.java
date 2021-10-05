package com.anderscore.refactoring.util;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static java.util.Arrays.asList;

@Component
public class DatabaseContainerHolder {

    private static final int CONTAINER_DB_PORT = 5432;
    private static final String DB_SCHEMA = "scheduler";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWD = "docker";

    private int hostDbPort;
    private JdbcDatabaseContainer<?> instance;

    public DatabaseContainerHolder(){
        hostDbPort = 0;
        refresh();
    }

    private JdbcDatabaseContainer<?> newContainer(){
        JdbcDatabaseContainer<?> container = new PostgreSQLContainer<>()
                .withDatabaseName(DB_SCHEMA)
                .withUsername(DB_USER)
                .withPassword(DB_PASSWD);

        if (hostDbPort > 0){
            container.setPortBindings(asList(hostDbPort + ":" + CONTAINER_DB_PORT));
        }

        container.start();

        return container;
    }


    public JdbcDatabaseContainer<?> get(){
        return instance;
    }

    public void refresh(){
        if (instance != null && instance.isRunning()){
            instance.stop();
        }

        instance = newContainer();
        hostDbPort = instance.getMappedPort(CONTAINER_DB_PORT);
    }
}