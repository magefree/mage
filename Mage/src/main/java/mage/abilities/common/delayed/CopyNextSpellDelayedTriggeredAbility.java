package mage.abilities.common.delayed;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public class CopyNextSpellDelayedTriggeredAbility extends CastNextSpellDelayedTriggeredAbility {

    public CopyNextSpellDelayedTriggeredAbility() {
        this(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY);
    }

    public CopyNextSpellDelayedTriggeredAbility(FilterSpell filter) {
        this(filter, new CopyTargetStackObjectEffect(true), null);
    }

    public CopyNextSpellDelayedTriggeredAbility(FilterSpell filter, Effect effect, String rule) {
        super(effect, filter, rule, true);
    }

    protected CopyNextSpellDelayedTriggeredAbility(final CopyNextSpellDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CopyNextSpellDelayedTriggeredAbility copy() {
        return new CopyNextSpellDelayedTriggeredAbility(this);
    }
}
