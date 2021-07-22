
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class ArcBlade extends CardImpl {

    public ArcBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");

        // Arc Blade deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        // Exile Arc Blade
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        // with three time counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.TIME.createInstance(), StaticValue.get(3), false, true);
        effect.setText("with three time counters on it");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Suspend 3-{2}{R}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{R}"), this));
    }

    private ArcBlade(final ArcBlade card) {
        super(card);
    }

    @Override
    public ArcBlade copy() {
        return new ArcBlade(this);
    }
}
