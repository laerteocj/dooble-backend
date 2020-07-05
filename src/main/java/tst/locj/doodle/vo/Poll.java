package tst.locj.doodle.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import lombok.Data;

/**
 * Data object that defines a Poll entry on the system, contains all the information need to handle
 * a poll data, including its options and participants.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = Id.NAME, property = "type")
@JsonSubTypes(
    value = {
      @JsonSubTypes.Type(value = TextPoll.class, name = "TEXT"),
      @JsonSubTypes.Type(value = DatePoll.class, name = "DATE")
    })
public class Poll {

  private String id;
  private String adminKey;
  private Instant lastestChange;
  private Instant initiated;
  private int participantsCount;
  private int inviteesCount;
  private PollType type;
  private int rowConstraint;
  private PreferencesType preferencesType;
  private PollState state;
  private Locale locale;
  private String title;
  private Initiator initiator;
  private String optionsHash;
  private List<Participant> participants;
  private List<String> invitees;
  private Device device;
  private PreferencesType levels;
}
