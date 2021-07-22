package mage.game.events;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ManaType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class ManaPaidEvent extends GameEvent {

    private final UUID sourcePaidId;
    private final MageObject sourceObject;
    private final ManaType manaType;

    public ManaPaidEvent(Ability abilityToPay, UUID manaSourceId, boolean manaFlag, UUID manaOriginalId, MageObject sourceObject, ManaType manaType) {
        super(GameEvent.EventType.MANA_PAID, abilityToPay.getId(), null, abilityToPay.getControllerId(), 0, manaFlag);
        this.setSourceId(manaSourceId);
        this.setData(manaOriginalId.toString());
        this.sourcePaidId = abilityToPay.getSourceId();
        this.sourceObject = sourceObject;
        this.manaType = manaType;
    }

    public UUID getSourcePaidId() {
        return sourcePaidId;
    }

    public MageObject getSourceObject() {
        return sourceObject;
    }

    public ManaType getManaType() {
        return manaType;
    }
}
