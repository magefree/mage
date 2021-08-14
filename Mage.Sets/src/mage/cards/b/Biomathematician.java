package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Biomathematician extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FRACTAL);

    public Biomathematician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Biomathematician enters the battlefield, create a 0/0 green and blue Fractal creature token. Put a +1/+1 counter on each Fractal you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FractalToken()));
        ability.addEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter));
        this.addAbility(ability);
    }

    private Biomathematician(final Biomathematician card) {
        super(card);
    }

    @Override
    public Biomathematician copy() {
        return new Biomathematician(this);
    }
}
