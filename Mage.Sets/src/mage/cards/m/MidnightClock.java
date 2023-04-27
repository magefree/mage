package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MidnightClock extends CardImpl {

    public MidnightClock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {2}{U}: Put an hour counter on Midnight Clock.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.HOUR.createInstance()), new ManaCostsImpl<>("{2}{U}")
        ));

        // At the beginning of each upkeep, put an hour counter on Midnight Clock.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.HOUR.createInstance()), TargetController.EACH_PLAYER, false
        ));

        // When the twelfth hour counter is put on Midnight Clock, shuffle your hand and graveyard into your library, then draw seven cards. Exile Midnight Clock.
        this.addAbility(new MidnightClockTriggeredAbility());
    }

    private MidnightClock(final MidnightClock card) {
        super(card);
    }

    @Override
    public MidnightClock copy() {
        return new MidnightClock(this);
    }
}

class MidnightClockTriggeredAbility extends TriggeredAbilityImpl {

    MidnightClockTriggeredAbility() {
        super(Zone.ALL, new MidnightClockEffect(), false);
    }

    private MidnightClockTriggeredAbility(final MidnightClockTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId())
                && event.getData().equals(CounterType.HOUR.getName())) {
            int amountAdded = event.getAmount();
            int hourCounters = amountAdded;
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent == null) {
                sourcePermanent = game.getPermanentEntering(getSourceId());
            }
            if (sourcePermanent != null) {
                hourCounters = sourcePermanent.getCounters(game).getCount(CounterType.HOUR);
            }
            return hourCounters - amountAdded < 12
                    && 12 <= hourCounters;
        }
        return false;
    }

    @Override
    public MidnightClockTriggeredAbility copy() {
        return new MidnightClockTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When the twelfth hour counter is put on {this}, " +
                "shuffle your hand and graveyard into your library, " +
                "then draw seven cards. Exile {this}.";
    }
}

class MidnightClockEffect extends OneShotEffect {

    private static final Effect effect = new ExileSourceEffect();

    MidnightClockEffect() {
        super(Outcome.Benefit);
    }

    private MidnightClockEffect(final MidnightClockEffect effect) {
        super(effect);
    }

    @Override
    public MidnightClockEffect copy() {
        return new MidnightClockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getHand());
        cards.addAll(player.getGraveyard());
        player.putCardsOnTopOfLibrary(cards, game, source, false);
        player.shuffleLibrary(source, game);
        player.drawCards(7, source, game);
        return effect.apply(game, source);
    }
}