package tst.locj.doodle.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import tst.locj.doodle.vo.Poll;

@Repository
public interface PollRepository
    extends ReactiveMongoRepository<Poll, String>, CustomPollRepositoryQueries {

  /**
   * Find any poll that contains the title informed. This query is case sensitive
   *
   * @param title the title to search for
   * @return a stream of polls that contains such title, otherwise empty
   */
  Flux<Poll> findByTitleLike(String title);

  /**
   * Find any poll that contains the initiator email equals the informed parameter. This query is
   * case sensitive
   *
   * @param email the email of the user that initiated the poll
   * @return a stream of polls that contains such initiator email, otherwise empty
   */
  Flux<Poll> findByInitiatorEmail(String email);
}
