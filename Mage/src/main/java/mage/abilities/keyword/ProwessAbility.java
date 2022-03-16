package mage.abilities.keyword;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 * @author LevelX2
 */
public class ProwessAbility extends SpellCastControllerTriggeredAbility {

    public ProwessAbility() {
        super(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false);
        this.filter = StaticFilters.FILTER_SPELL_NON_CREATURE;
    }

    public ProwessAbility(final ProwessAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Prowess <i>(Whenever you cast a noncreature spell, this creature gets +1/+1 until end of turn.)</i>";
    }

    @Override
    public ProwessAbility copy() {
        return new ProwessAbility(this);
    }
}
