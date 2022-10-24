
package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.StaticAbility;
import mage.target.Target;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class EnchantAbility extends StaticAbility {

    protected String targetName;

    public EnchantAbility(Target target) {
        super(Zone.BATTLEFIELD, null);
        this.targetName = target.getTargetName();
    }

    public EnchantAbility(final EnchantAbility ability) {
        super(ability);
        this.targetName = ability.targetName;
    }

    @Override
    public EnchantAbility copy() {
        return new EnchantAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        sb.append("Enchant ").append(targetName);
        if (!this.getEffects().isEmpty()) {
            sb.append(". ").append(super.getRule());
        }
        return sb.toString();
    }
}
