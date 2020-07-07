package tst.locj.doodle.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import junitx.framework.ListAssert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tst.locj.doodle.DoodleTestConfiguration;
import tst.locj.doodle.vo.Poll;

/**
 * This unit test class tests the annotations and converters for the Poll Repository. This code is
 * using spring data + MongoDB, so the repository class itself doesn't have any concrete
 * implementation. But we are using converters, which use Jackson, to serialize/deserialize the Poll
 * object.<br>
 * This unit class aims to test if the converters as well as the annotations are working correctly.
 * <br>
 * Object Mapper is being loaded manually instead of Autowired because we will use on the loadAssets
 * method that uses only once in the test (and require to be a static method).
 */
@RunWith(SpringRunner.class)
@DataMongoTest(includeFilters = @ComponentScan.Filter(Configuration.class))
public class PollRepositoryTest {

  private static List<Poll> ASSETS = null;
  private static ObjectMapper OBJECT_MAPPER = null;
  @Autowired public PollRepository pollRepository;

  @BeforeClass
  public static void loadAssets() throws Exception {
    DoodleTestConfiguration doodleTestConfiguration = new DoodleTestConfiguration();
    OBJECT_MAPPER = doodleTestConfiguration.objectMapper();

    ClassPathResource resource = new ClassPathResource("assets/polls.json");
    ASSETS = OBJECT_MAPPER.readValue(resource.getInputStream(), new TypeReference<List<Poll>>() {});
  }

  @Before
  public void loadObjectMapper() {}

  public void loadAssetsToDB() {}

  @Test
  public void testSave() {
    Flux<Poll> saveAll = pollRepository.saveAll(ASSETS);

    StepVerifier.create(saveAll).expectNextCount(ASSETS.size()).verifyComplete();
  }

  @Test
  public void testConverters() throws Exception {
    // Test the writer storing all the objects
    pollRepository.saveAll(ASSETS).collectList().block();

    // Test the reader loading all the objects
    List<Poll> loadedPolls = pollRepository.findAll().collectList().block();

    // Compare the ASSETS objects with the loaded to ensure that the storing and reading doesn't
    // corrupt any data.
    ListAssert.assertEquals(ASSETS, loadedPolls);
  }

  @Test
  public void testIdRespected() throws Exception {
    pollRepository.saveAll(ASSETS).blockLast();
    Poll firstItem = ASSETS.get(0);

    Mono<Poll> findById = pollRepository.findById(firstItem.getId());
    StepVerifier.create(findById).expectNext(firstItem).verifyComplete();
  }

  @Test
  public void testFindByTitleLike() {
    pollRepository.saveAll(ASSETS).collectList().block();

    String queryTitle = "Marvel";

    // Get the list of polls from the ASSETS that contains Marvel in the title
    List<Poll> expectedPolls =
        ASSETS.stream()
            .filter(poll -> poll.getTitle().contains(queryTitle))
            .collect(Collectors.toList());

    // Load from the DB and compare with the expected list.
    List<Poll> loadedPolls = pollRepository.findByTitleLike(queryTitle).collectList().block();

    ListAssert.assertEquals(expectedPolls, loadedPolls);
  }

  @Test
  public void testFindByTitleLikeEmpty() {
    pollRepository.saveAll(ASSETS).collectList().block();

    // Load from the DB and compare with the expected list.
    Flux<Poll> loadedPolls = pollRepository.findByTitleLike("SomethingThatDoesntExist");

    StepVerifier.create(loadedPolls).expectNextCount(0).verifyComplete();
  }

  @Test
  public void testFindByInitiatedBetween() {
    // Load the assets
    pollRepository.saveAll(ASSETS).collectList().block();

    LocalDate startDate = LocalDate.parse("2017-01-01");
    Instant _startDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

    // Get the list of polls from the ASSETS that contains Marvel in the title
    List<Poll> expectedPolls =
        ASSETS.stream()
            .filter(
                poll -> {
                  return !poll.getInitiated().isBefore(_startDate);
                })
            .collect(Collectors.toList());

    List<Poll> loadedPolls = pollRepository.findByInitiatedSince(startDate).collectList().block();

    ListAssert.assertEquals(expectedPolls, loadedPolls);
  }

  @Test
  public void testFindByInitiatedBetweenEmpty() {
    // Load the assets
    pollRepository.saveAll(ASSETS).collectList().block();

    LocalDate startDate = LocalDate.parse("2018-01-01");

    Flux<Poll> loadedPolls = pollRepository.findByInitiatedSince(startDate);

    StepVerifier.create(loadedPolls).expectNextCount(0).verifyComplete();
  }

  @Test
  public void testFindByInitiatorEmail() {
    // Load the assets
    pollRepository.saveAll(ASSETS).collectList().block();

    String emailQuery = "mh+sample@doodle.com";

    // Get the list of polls from the ASSETS that contains Marvel in the title
    List<Poll> expectedPolls =
        ASSETS.stream()
            .filter(
                poll ->
                    poll.getInitiator() != null
                        && emailQuery.equals(poll.getInitiator().getEmail()))
            .collect(Collectors.toList());

    List<Poll> loadedPolls = pollRepository.findByInitiatorEmail(emailQuery).collectList().block();

    ListAssert.assertEquals(expectedPolls, loadedPolls);
  }

  @Test
  public void testFindByInitiatorEmailEmpty() {
    // Load the assets
    pollRepository.saveAll(ASSETS).collectList().block();

    String emailQuery = "something@test.com";

    Flux<Poll> loadedPolls = pollRepository.findByInitiatorEmail(emailQuery);

    StepVerifier.create(loadedPolls).expectNextCount(0).verifyComplete();
  }
}
