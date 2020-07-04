package tst.locj.doodle.vo;

import java.util.List;
import lombok.Data;

/** The data that defines a participant on a poll */
@Data
public class Participant {

  private int id;
  private String name;
  private List<Integer> preferences;
}
