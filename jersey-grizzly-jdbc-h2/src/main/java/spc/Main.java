package spc;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            RecordRepository.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RecordRepository repository = new RecordRepository();
        RecordObject record = new RecordObject();
        record.setOrigin("J");
        for (int i = 0; i < 20; i++) {
            repository.insertRecord(record);
        }
        List<RecordObject> records = repository.selectRecords();
        records.forEach(r -> System.out.printf("%s %s\n", r.getId(), r.getOrigin()));

        URI url = URI.create("http://0.0.0.0:8080");
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages(Main.class.getPackageName());
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(RecordRepository.class);
            }
        });
        GrizzlyHttpServerFactory.createHttpServer(url, resourceConfig);


        // TODO
        // repository controller
    }
}
