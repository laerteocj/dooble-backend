package tst.locj.doodle.repository;

import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import tst.locj.doodle.vo.Poll;

public class CustomPollRepositoryQueriesImpl implements CustomPollRepositoryQueries {

  @Autowired ReactiveMongoTemplate mongoTemplate;

  @Override
  public Flux<Poll> findByInitiatedSince(LocalDate since) {
    long startDateAsTimestamp =
        since.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

    Query query = new Query((Criteria.where("initiated").gte(startDateAsTimestamp)));

    return mongoTemplate.find(query, Poll.class);
  }
}
