
package mage.abilities.condition.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class EnchantedSourceCondition implements Condition {

    private int numberOfEnchantments;
    private ComparisonType comparisonType;
    private boolean aurasOnly;

    public EnchantedSourceCondition() {
        this(1);
    }

    public EnchantedSourceCondition(int numberOfEnchantments) {
        this(numberOfEnchantments, ComparisonType.OR_GREATER, false);
    }

    public EnchantedSourceCondition(int numberOfEnchantments, ComparisonType comparisonType, boolean aurasOnly) {
        this.numberOfEnchantments = numberOfEnchantments;
        this.comparisonType = comparisonType;
        this.aurasOnly = aurasOnly;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        int numberOfFoundEnchantments = 0;
        if (permanent != null) {
            for (UUID uuid : permanent.getAttachments()) {
                Permanent attached = game.getBattlefield().getPermanent(uuid);
                if (attached != null && attached.isEnchantment(game) && (!aurasOnly || attached.hasSubtype(SubType.AURA, game))) {
                    numberOfFoundEnchantments += 1;
                }
            }
        }
        return ComparisonType.compare(numberOfFoundEnchantments, comparisonType, numberOfEnchantments);
    }

    @Override
    public String toString() {
        return "enchanted";
    }
}
