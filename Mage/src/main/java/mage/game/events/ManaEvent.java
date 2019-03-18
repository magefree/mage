

package mage.game.events;

import java.util.UUID;
import mage.Mana;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ManaEvent extends GameEvent {

    protected Mana mana;

    public ManaEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, Mana mana) {
        super(type, targetId, sourceId, playerId);
        this.mana = mana;
    }

    public Mana getMana() {
        return mana;
    }

}
