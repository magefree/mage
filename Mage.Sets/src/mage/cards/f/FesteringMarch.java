
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class FesteringMarch extends CardImpl {

    public FesteringMarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Creatures your opponents control get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostOpponentsEffect(-1, -1, Duration.EndOfTurn));
        // Exile Festering March
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        // with three time counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.TIME.createInstance(), StaticValue.get(3), false, true);
        effect.setText("with three time counters on it");
        this.getSpellAbility().addEffect(effect);

        // Suspend 3-{2}{B}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{B}"), this));
    }

    private FesteringMarch(final FesteringMarch card) {
        super(card);
    }

    @Override
    public FesteringMarch copy() {
        return new FesteringMarch(this);
    }
}
