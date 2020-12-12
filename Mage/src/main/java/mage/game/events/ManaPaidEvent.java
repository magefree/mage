package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class ManaPaidEvent extends GameEvent {

    public ManaPaidEvent(Ability abilityToPay, UUID manaSourceId, boolean manaFlag, UUID manaOriginalId) {
        super(GameEvent.EventType.MANA_PAID, abilityToPay.getId(), null, abilityToPay.getControllerId(), 0, manaFlag);
        this.setSourceId(manaSourceId);
        this.setData(manaOriginalId.toString());
    }
}
