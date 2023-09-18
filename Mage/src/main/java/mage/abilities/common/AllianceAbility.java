package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public class AllianceAbility extends EntersBattlefieldControlledTriggeredAbility {

    public AllianceAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false);
        this.setAbilityWord(AbilityWord.ALLIANCE);
    }

    private AllianceAbility(final AllianceAbility ability) {
        super(ability);
    }

    @Override
    public AllianceAbility copy() {
        return new AllianceAbility(this);
    }
}
