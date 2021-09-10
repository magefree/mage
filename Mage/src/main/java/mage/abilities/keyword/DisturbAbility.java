package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 * @author weirddan455
 * TODO: this is currently implemented in a pull request
 */
public class DisturbAbility extends SpellAbility {

    public DisturbAbility(Cost cost) {
        this(cost, TimingRule.SORCERY);
    }

    public DisturbAbility(Cost cost, TimingRule timingRule) {
        super(null, "", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE);
    }
}
