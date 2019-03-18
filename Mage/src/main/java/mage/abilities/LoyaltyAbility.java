
package mage.abilities;

import mage.abilities.costs.common.PayLoyaltyCost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LoyaltyAbility extends ActivatedAbilityImpl {

    public LoyaltyAbility(Effect effect, int loyalty) {
        super(Zone.BATTLEFIELD, effect, new PayLoyaltyCost(loyalty));
        this.timing = TimingRule.SORCERY;
    }

    public LoyaltyAbility(Effects effects, int loyalty) {
        super(Zone.BATTLEFIELD, effects, new PayLoyaltyCost(loyalty));
        this.timing = TimingRule.SORCERY;
    }

    public LoyaltyAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, new PayVariableLoyaltyCost());
        this.timing = TimingRule.SORCERY;
    }

    public LoyaltyAbility(Effects effects) {
        super(Zone.BATTLEFIELD, effects, new PayVariableLoyaltyCost());
        this.timing = TimingRule.SORCERY;
    }

    public LoyaltyAbility(LoyaltyAbility ability) {
        super(ability);
    }

    @Override
    public LoyaltyAbility copy() {
        return new LoyaltyAbility(this);
    }

}
