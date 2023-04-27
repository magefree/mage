package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.BiogenicOozeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiogenicOoze extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Ooze you control");

    static {
        filter.add(SubType.OOZE.getPredicate());
    }

    public BiogenicOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Biogenic Ooze enters the battlefield, create a 2/2 green Ooze creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new BiogenicOozeToken())
        ));

        // At the beginning if your end step, put a +1/+1 counter on each Ooze you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter),
                TargetController.YOU, false
        ));

        // {1}{G}{G}{G}: Create a 2/2 green Ooze creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new BiogenicOozeToken()),
                new ManaCostsImpl<>("{1}{G}{G}{G}")
        ));
    }

    private BiogenicOoze(final BiogenicOoze card) {
        super(card);
    }

    @Override
    public BiogenicOoze copy() {
        return new BiogenicOoze(this);
    }
}
