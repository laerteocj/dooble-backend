package tst.locj.doodle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tst.locj.doodle.vo.Poll;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class DemoApplicationTests {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void contextLoads() throws Exception {
    objectMapper.registerModule(new JavaTimeModule());
    ClassPathResource resource = new ClassPathResource("assets/polls.json");
    List<Poll> polls =
        objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Poll>>() {});
    System.out.println(polls);

    List<Map<String, Object>> maps =
        objectMapper.readValue(
            resource.getInputStream(), new TypeReference<List<Map<String, Object>>>() {});
    System.out.println(maps);
  }
}
