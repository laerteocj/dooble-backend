package tst.locj.doodle.repository;

import java.time.LocalDate;
import reactor.core.publisher.Flux;
import tst.locj.doodle.vo.Poll;

/**
 * Interface that offers a customized list of query methods that will be appended to the
 * PollRepository.
 */
public interface CustomPollRepositoryQueries {

  /**
   * Query all the Polls that where initiated since the informed date. It will assume that the date
   * is meant to start since the 0 hour of the informed date.
   *
   * @param since since date to query, it is inclusive
   * @return polls that where initiated since the informed date, if nothing matches it will return
   *     an empty.
   */
  Flux<Poll> findByInitiatedSince(LocalDate since);
}
