package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class EnchantAbility extends StaticAbility {

    protected String targetName;

    public EnchantAbility(FilterCard filter) {
        super(Zone.BATTLEFIELD, null);
        this.targetName = filter.getMessage();
    }

    public EnchantAbility(FilterPermanent filter) {
        super(Zone.BATTLEFIELD, null);
        this.targetName = filter.getMessage();
    }

    public EnchantAbility(FilterPlayer filter) {
        super(Zone.BATTLEFIELD, null);
        this.targetName = filter.getMessage();
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
