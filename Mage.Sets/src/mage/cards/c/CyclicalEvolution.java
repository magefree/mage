
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CyclicalEvolution extends CardImpl {

    public CyclicalEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");

        // Target creature gets +3/+3 until end of turn. Exile Cyclical Evolution with three time counters on it.
        getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
        getSpellAbility().addEffect(new ExileSpellEffect());
        Effect effect = new AddCountersSourceEffect(CounterType.TIME.createInstance(), StaticValue.get(3), true, true);
        effect.setText("with three time counters on it");
        getSpellAbility().addEffect(effect);

        // Suspend 3-{2}{G}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{G}"), this));
    }

    private CyclicalEvolution(final CyclicalEvolution card) {
        super(card);
    }

    @Override
    public CyclicalEvolution copy() {
        return new CyclicalEvolution(this);
    }
}
