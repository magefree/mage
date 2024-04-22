package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.MilledCardsEvent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWiseMothman extends CardImpl {

    public TheWiseMothman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever The Wise Mothman enters the battlefield or attacks, each player gets a rad counter.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.RAD.createInstance(), TargetController.EACH_PLAYER)
        ));

        // Whenever one or more nonland cards are milled, put a +1/+1 counter on each of up to X target creatures, where X is the number of nonland cards milled this way.
        this.addAbility(new TheWiseMothmanTriggeredAbility());
    }

    private TheWiseMothman(final TheWiseMothman card) {
        super(card);
    }

    @Override
    public TheWiseMothman copy() {
        return new TheWiseMothman(this);
    }
}

class TheWiseMothmanTriggeredAbility extends TriggeredAbilityImpl {

    TheWiseMothmanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on each of up to X target creatures, " +
                        "where X is the number of nonland cards milled this way"));
        this.setTriggerPhrase("Whenever one or more nonland cards are milled, ");
    }

    private TheWiseMothmanTriggeredAbility(final TheWiseMothmanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheWiseMothmanTriggeredAbility copy() {
        return new TheWiseMothmanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MILLED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int count = ((MilledCardsEvent) event).getCards().count(StaticFilters.FILTER_CARD_NON_LAND, game);
        if (count < 1) {
            return false;
        }
        this.getTargets().clear();
        this.getTargets().add(new TargetCreaturePermanent(0, count));
        return true;
    }
}
