package tst.locj.doodle.vo;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DatePoll extends Poll {

  private List<PollDateOption> options;
}
