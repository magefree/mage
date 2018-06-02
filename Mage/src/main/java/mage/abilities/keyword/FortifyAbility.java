

package mage.abilities.keyword;

import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.AttachEffect;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */

//20091005 - 702.64
public class FortifyAbility extends ActivatedAbilityImpl {
    public FortifyAbility(Zone zone, AttachEffect effect, Cost cost) {
        super(zone, effect, cost);
        this.addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        timing = TimingRule.SORCERY;
    }

    public FortifyAbility(final FortifyAbility ability) {
        super(ability);
    }

    @Override
    public FortifyAbility copy() {
        return new FortifyAbility(this);
    }
}