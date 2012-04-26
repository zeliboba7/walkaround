package com.google.walkaround.wave.server.robot;

import com.google.common.collect.Lists;
import com.google.walkaround.util.shared.Assert;

import org.waveprotocol.wave.model.wave.ParticipantId;

import java.util.List;

/**
 * Helper for identifying participant IDs that are robots.
 *
 * @author ljv@google.com (Lennard de Rijk)
 */
public class RobotIdHelper {

  private static final String APPENGINE_URL_FMT = "http://%s.appspot.com";

  private RobotIdHelper() {
  }

  /**
   * Returns all {@link ParticipantId}s that represent a robot.
   */
  public static List<ParticipantId> getAllRobotIds(List<ParticipantId> participants) {
    List<ParticipantId> robotIds = Lists.newArrayList();
    for (ParticipantId participant : participants) {
      if (isRobotId(participant)) {
        robotIds.add(participant);
      }
    }
    return robotIds;
  }

  /**
   * Returns true if the list of participants contains a robot.
   */
  public static boolean containsRobotId(List<ParticipantId> participants) {
    for (ParticipantId participant : participants) {
      if (isRobotId(participant)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns true if the particpant is a robot. This method will need to change
   * significantly if we allow robots from other domains. It works well for a
   * first-version where we support only AppEngine robots.
   */
  public static boolean isRobotId(ParticipantId participant) {
    return participant.getDomain().equals("appspot.com");
  }

  /**
   * Returns the base URL of where the robot can be reached.
   */
  public static String getRobotURL(ParticipantId participant) {
    Assert.check(isRobotId(participant));
    String address = participant.getAddress();
    return String.format(APPENGINE_URL_FMT, address.substring(0, address.indexOf('@')));
  }
}