package mage.cards.w;

import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaveGoodbye extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(CounterType.P1P1.getPredicate()));
    }

    public WaveGoodbye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Return each creature without a +1/+1 counter on it to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter)
                .setText("return each creature without a +1/+1 counter on it to its owner's hand"));
    }

    private WaveGoodbye(final WaveGoodbye card) {
        super(card);
    }

    @Override
    public WaveGoodbye copy() {
        return new WaveGoodbye(this);
    }
}
