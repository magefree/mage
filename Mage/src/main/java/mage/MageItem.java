

package mage;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
@FunctionalInterface
public interface MageItem extends Serializable {

    UUID getId();
}
