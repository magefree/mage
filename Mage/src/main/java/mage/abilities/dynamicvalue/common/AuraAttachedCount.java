
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
 * @author North
 */
public class AuraAttachedCount implements DynamicValue {

    private Integer amount;

    public AuraAttachedCount() {
        this(1);
    }

    public AuraAttachedCount(Integer amount) {
        this.amount = amount;
    }

    public AuraAttachedCount(final AuraAttachedCount dynamicValue) {
        this.amount = dynamicValue.amount;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Permanent p = game.getPermanent(sourceAbility.getSourceId());
        if (p != null) {
            List<UUID> attachments = p.getAttachments();
            for (UUID attachmentId : attachments) {
                Permanent attached = game.getPermanent(attachmentId);
                if (attached != null && attached.hasSubtype(SubType.AURA, game)) {
                    count++;
                }
            }

        }
        return amount * count;
    }

    @Override
    public AuraAttachedCount copy() {
        return new AuraAttachedCount(this);
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
        return "Aura attached to it";
    }
}
