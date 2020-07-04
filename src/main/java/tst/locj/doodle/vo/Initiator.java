package tst.locj.doodle.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Initiator {

  private String name;
  private String email;
  private boolean notify;
}
