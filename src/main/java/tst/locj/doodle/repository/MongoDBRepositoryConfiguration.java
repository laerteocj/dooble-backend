package tst.locj.doodle.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.ServerAddress;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions.MongoConverterConfigurationAdapter;

/**
 * Project configuration for the mongo repository, needed so we can add 2 converters {@link
 * PollWriterConverter}, which convert the Poll object to a mongoDB document, and {@link
 * PollReaderConverter} which convert the MongoDB document to the Poll object.
 */
@Configuration
public class MongoDBRepositoryConfiguration extends AbstractReactiveMongoConfiguration {

  @Value("${tst.locj.doodle.mongdb.cluster.host:localhost}")
  private String clusterHost;

  @Autowired private ObjectMapper objectMapper;

  @Override
  protected void configureClientSettings(Builder builder) {
    builder.applyToClusterSettings(
        builder1 -> {
          ServerAddress serverAddress = new ServerAddress(clusterHost);
          builder1.hosts(List.of(serverAddress));
        });
  }

  @Override
  protected String getDatabaseName() {
    return "doodle-poll";
  }

  @Override
  protected void configureConverters(
      MongoConverterConfigurationAdapter converterConfigurationAdapter) {
    converterConfigurationAdapter.registerConverter(new PollWriterConverter(objectMapper));
    converterConfigurationAdapter.registerConverter(new PollReaderConverter(objectMapper));
  }
}
