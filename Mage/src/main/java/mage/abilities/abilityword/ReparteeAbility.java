package mage.abilities.abilityword;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;

/**
 * @author TheElk801
 */
public class ReparteeAbility extends SpellCastControllerTriggeredAbility {

    private static final FilterSpell filter1 = new FilterInstantOrSorcerySpell("an instant or sorcery spell that targets a creature");

    static {
        filter1.add(new TargetsPermanentPredicate(StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    public ReparteeAbility(Effect effect) {
        this(effect, false);
    }

    public ReparteeAbility(Effect effect, boolean optional) {
        super(effect, filter1, optional);
        this.setAbilityWord(AbilityWord.REPARTEE);
    }

    protected ReparteeAbility(final ReparteeAbility ability) {
        super(ability);
    }

    @Override
    public ReparteeAbility copy() {
        return new ReparteeAbility(this);
    }
}
