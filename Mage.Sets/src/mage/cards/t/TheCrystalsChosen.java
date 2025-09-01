package mage.cards.t;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HeroToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCrystalsChosen extends CardImpl {

    public TheCrystalsChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");

        // Create four 1/1 colorless Hero creature tokens. Then put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HeroToken(), 4));
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ).concatBy("Then"));
    }

    private TheCrystalsChosen(final TheCrystalsChosen card) {
        super(card);
    }

    @Override
    public TheCrystalsChosen copy() {
        return new TheCrystalsChosen(this);
    }
}
