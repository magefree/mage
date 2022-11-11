package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North, noxx
 */
public class EquipmentAttachedCount implements DynamicValue {

    private final int multiplier;

    public EquipmentAttachedCount() {
        this(1);
    }

    public EquipmentAttachedCount(int multiplier) {
        this.multiplier = multiplier;
    }

    public EquipmentAttachedCount(final EquipmentAttachedCount dynamicValue) {
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Permanent permanent = game.getPermanent(sourceAbility.getSourceId()); // don't change this - may affect other cards
        if (permanent != null) {
            List<UUID> attachments = permanent.getAttachments();
            for (UUID attachmentId : attachments) {
                Permanent attached = game.getPermanent(attachmentId);
                if (attached != null && attached.hasSubtype(SubType.EQUIPMENT, game)) {
                    count++;
                }
            }
        }
        return multiplier * count;
    }

    @Override
    public EquipmentAttachedCount copy() {
        return new EquipmentAttachedCount(this);
    }

    @Override
    public String toString() {
        return Integer.toString(multiplier);
    }

    @Override
    public String getMessage() {
        return "Equipment attached to it";
    }

    @Override
    public int getSign() {
        return multiplier;
    }
}
