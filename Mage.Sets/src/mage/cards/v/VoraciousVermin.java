package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VoraciousVermin extends CardImpl {

    public VoraciousVermin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Voracious Vermin enters the battlefield, create a 1/1 black Rat creature token with "This creature can't block."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RatCantBlockToken())));

        // Whenever another creature you control dies, put a +1/+1 counter on Voracious Vermin.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));
    }

    private VoraciousVermin(final VoraciousVermin card) {
        super(card);
    }

    @Override
    public VoraciousVermin copy() {
        return new VoraciousVermin(this);
    }
}
