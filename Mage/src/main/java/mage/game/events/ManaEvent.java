package mage.game.events;

import mage.Mana;
import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ManaEvent extends GameEvent {

    protected Mana mana;

    public ManaEvent(EventType type, UUID targetId, Ability source, UUID playerId, Mana mana) {
        super(type, targetId, source, playerId, mana.count(), false);
        this.mana = mana;
    }

    public Mana getMana() {
        return mana;
    }
}
