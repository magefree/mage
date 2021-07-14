
package mage.abilities.condition.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class EnchantedSourceCondition implements Condition {

    private int numberOfEnchantments;

    public EnchantedSourceCondition() {
        this(1);
    }

    public EnchantedSourceCondition(int numberOfEnchantments) {
        this.numberOfEnchantments = numberOfEnchantments;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        int numberOfFoundEnchantments = 0;
        if (permanent != null) {
            for (UUID uuid : permanent.getAttachments()) {
                Permanent attached = game.getBattlefield().getPermanent(uuid);
                if (attached != null && attached.isEnchantment(game)) {
                    if (++numberOfFoundEnchantments >= numberOfEnchantments) {
                        return true;
                    }
                }
            }
        }
        return (numberOfFoundEnchantments >= numberOfEnchantments);
    }

    @Override
    public String toString() {
        return "enchanted";
    }
}
