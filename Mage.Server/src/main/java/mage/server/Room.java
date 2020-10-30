

package mage.server;

import java.rmi.Remote;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Room extends Remote {

    UUID getChatId();
    UUID getRoomId();
}
