package mage.cards.u;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnitedFront extends CardImpl {

    public UnitedFront(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}");

        // Create X 1/1 white Ally creature tokens, then put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new AllyToken(), GetXValue.instance));
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ).concatBy(", then"));
    }

    private UnitedFront(final UnitedFront card) {
        super(card);
    }

    @Override
    public UnitedFront copy() {
        return new UnitedFront(this);
    }
}
