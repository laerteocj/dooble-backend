package tst.locj.doodle.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollDateOption implements PollOption {

  private Instant start;
  private Instant end;
  private boolean allday;
  private Instant startDate;
  private Instant endDate;
  private boolean available;
}
