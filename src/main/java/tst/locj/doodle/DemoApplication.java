package tst.locj.doodle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import tst.locj.doodle.repository.PollRepository;
import tst.locj.doodle.vo.Poll;

@Slf4j
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext =
        SpringApplication.run(DemoApplication.class, args);

    DoodleTestConfiguration configuration =
        applicationContext.getBean(DoodleTestConfiguration.class);
    if (configuration.isLoadDataFromFile()) {
      log.info("Adding polls from asset file to the database");
      ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
      PollRepository pollRepository = applicationContext.getBean(PollRepository.class);
      ClassPathResource resource = new ClassPathResource("assets/polls.json");
      try {
        List<Poll> pollsToAdd =
            objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Poll>>() {});
        pollRepository
            .saveAll(pollsToAdd)
            .subscribe(
                pollAdded -> {
                  log.info("Poll with id {} added to the database.", pollAdded.getId());
                });
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }
}
