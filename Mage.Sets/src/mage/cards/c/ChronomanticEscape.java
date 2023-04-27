
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author spjspj
 */
public final class ChronomanticEscape extends CardImpl {


    public ChronomanticEscape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Until your next turn, creatures can't attack you. Exile Chronomantic Escape with three time counters on it.
        getSpellAbility().addEffect(new CantAttackYouAllEffect(Duration.UntilYourNextTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
        getSpellAbility().addEffect(new ExileSpellEffect());
        Effect effect = new AddCountersSourceEffect(CounterType.TIME.createInstance(), StaticValue.get(3), true, true);
        effect.setText("with three time counters on it");
        getSpellAbility().addEffect(effect);

        // Suspend 3-{2}{W}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{W}"), this));
    }

    private ChronomanticEscape(final ChronomanticEscape card) {
        super(card);
    }

    @Override
    public ChronomanticEscape copy() {
        return new ChronomanticEscape(this);
    }
}
