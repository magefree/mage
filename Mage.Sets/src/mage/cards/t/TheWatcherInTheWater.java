package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TentacleToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWatcherInTheWater extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.TENTACLE, "a Tentacle you control");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.KRAKEN, "Kraken");

    public TheWatcherInTheWater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // The Watcher in the Water enters the battlefield tapped with nine stun counters on it.
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true), false, null,
                "{this} enters the battlefield tapped with nine stun counters on it.", null
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.STUN.createInstance(9)));
        this.addAbility(ability);

        // Whenever you draw a card during an opponent's turn, create a 1/1 blue Tentacle creature token.
        this.addAbility(new ConditionalTriggeredAbility(
                new DrawCardControllerTriggeredAbility(
                        new CreateTokenEffect(new TentacleToken()), false
                ), OpponentsTurnCondition.instance, "Whenever you draw a card " +
                "during an opponent's turn, create a 1/1 blue Tentacle creature token."
        ));

        // Whenever a Tentacle you control dies, untap up to one target Kraken and put a stun counter on up to one target nonland permanent.
        ability = new DiesCreatureTriggeredAbility(new UntapTargetEffect(), false, filter);
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("and put a stun counter on up to one target nonland permanent")
                .setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetPermanent(0, 1, filter2));
        ability.addTarget(new TargetNonlandPermanent(0, 1));
        this.addAbility(ability);
    }

    private TheWatcherInTheWater(final TheWatcherInTheWater card) {
        super(card);
    }

    @Override
    public TheWatcherInTheWater copy() {
        return new TheWatcherInTheWater(this);
    }
}
