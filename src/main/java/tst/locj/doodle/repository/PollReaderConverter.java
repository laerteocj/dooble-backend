package tst.locj.doodle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import tst.locj.doodle.vo.Poll;

/**
 * Uses {@link ObjectMapper} to convert the @{@link Document} into the {@link Poll}. It is used to
 * keep the exercise assets the same as informed without changing structures like the dates (the
 * polls in the exercise use timestamp for date/time).
 */
public class PollReaderConverter implements Converter<Document, Poll> {

  private ObjectMapper mapper = new ObjectMapper();

  public PollReaderConverter(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public Poll convert(Document source) {
    try {
      return mapper.readValue(source.toJson(), Poll.class);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(
          "It wasn't possible to convert the stored information into the Poll class", e);
    }
  }
}
