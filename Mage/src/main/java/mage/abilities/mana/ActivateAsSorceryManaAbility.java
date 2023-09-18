
package mage.abilities.mana;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 * @author LevelX2
 */


public class ActivateAsSorceryManaAbility extends SimpleManaAbility {

    public ActivateAsSorceryManaAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, mana, cost);
        timing = TimingRule.SORCERY;
    }

    protected ActivateAsSorceryManaAbility(final ActivateAsSorceryManaAbility ability) {
        super(ability);
    }

    @Override
    public ActivateAsSorceryManaAbility copy() {
        return new ActivateAsSorceryManaAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only as a sorcery.";
    }
}
