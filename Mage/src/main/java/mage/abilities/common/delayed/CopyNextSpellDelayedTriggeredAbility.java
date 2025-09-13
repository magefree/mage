package mage.abilities.common.delayed;

import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public class CopyNextSpellDelayedTriggeredAbility extends CastNextSpellDelayedTriggeredAbility {

    public CopyNextSpellDelayedTriggeredAbility() {
        this(StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY);
    }

    public CopyNextSpellDelayedTriggeredAbility(FilterSpell filter) {
        this(filter, 1);
    }

    public CopyNextSpellDelayedTriggeredAbility(FilterSpell filter, int amount) {
        super(new CopyTargetStackObjectEffect(false, true, true, amount, null), filter, true);
    }

    protected CopyNextSpellDelayedTriggeredAbility(final CopyNextSpellDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CopyNextSpellDelayedTriggeredAbility copy() {
        return new CopyNextSpellDelayedTriggeredAbility(this);
    }
}
