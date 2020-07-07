package tst.locj.doodle.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import tst.locj.doodle.vo.Poll;

/**
 * Uses {@link ObjectMapper} to convert the @{@link Poll} into the {@link Document}. It is used to
 * keep the exercise assets the same as informed without changing structures like the dates (the
 * polls in the exercise use timestamp for date/time).
 */
public class PollWriterConverter implements Converter<Poll, Document> {

  private ObjectMapper mapper = new ObjectMapper();

  public PollWriterConverter(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public Document convert(Poll source) {
    Map<String, Object> _source = mapper.convertValue(source, Map.class);
    Document documentToStore = new Document(_source);

    // Add the field _id that is used by MongoDB as the object id, otherwise it will generate one on
    // its own
    documentToStore.append("_id", source.getId());

    return documentToStore;
  }
}
