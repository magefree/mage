
package mage.abilities.dynamicvalue.common;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North, noxx
 */
public class EquipmentAttachedCount implements DynamicValue {

    private Integer amount;

    public EquipmentAttachedCount() {
        this(1);
    }

    public EquipmentAttachedCount(Integer amount) {
        this.amount = amount;
    }

    public EquipmentAttachedCount(final EquipmentAttachedCount dynamicValue) {
        this.amount = dynamicValue.amount;
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        Permanent permanent = game.getPermanent(source.getSourceId()); // don't change this - may affect other cards
        if (permanent != null) {
            List<UUID> attachments = permanent.getAttachments();
            for (UUID attachmentId : attachments) {
                Permanent attached = game.getPermanent(attachmentId);
                if (attached != null && attached.hasSubtype(SubType.EQUIPMENT, game)) {
                    count++;
                }
            }
        }
        return amount * count;
    }

    @Override
    public EquipmentAttachedCount copy() {
        return new EquipmentAttachedCount(this);
    }

    @Override
    public String toString() {
        if (amount != null) {
            return amount.toString();
        }
        return "";
    }

    @Override
    public String getMessage() {
        return "Equipment attached to it";
    }
}
