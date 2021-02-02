
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author JayDi85
 */
public final class StrengthOfThePack extends CardImpl {

    public StrengthOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}{G}");

        // Put two +1/+1 counters on each creature you control.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(2), new FilterControlledCreaturePermanent()));
    }

    private StrengthOfThePack(final StrengthOfThePack card) {
        super(card);
    }

    @Override
    public StrengthOfThePack copy() {
        return new StrengthOfThePack(this);
    }
}