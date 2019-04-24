
package mage.cards.h;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class HuntToExtinction extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a bounty counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.BOUNTY));
    }

    public HuntToExtinction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}{R}{G}");

        // Put a bounty counter on up to one target creature an opponent controls.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(0, 1));

        // Hunt to Extinction deals X damage to each creature. 
        this.getSpellAbility().addEffect(new DamageAllEffect(new ManacostVariableValue(), new FilterCreaturePermanent()));

        // Hunt to Exctinction deals an additional X damage to each creature with a bounty counter on it.
        Effect effect = new DamageAllEffect(new ManacostVariableValue(), new FilterCreaturePermanent(filter));
        effect.setText("Hunt to Exctinction deals an additional X damage to each creature with a bounty counter on it");
        this.getSpellAbility().addEffect(effect);

    }

    public HuntToExtinction(final HuntToExtinction card) {
        super(card);
    }

    @Override
    public HuntToExtinction copy() {
        return new HuntToExtinction(this);
    }
}
