package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * 725.1. Rad counters are a kind of counter a player can have (see rule 122, "Counters").
 * There is an inherent triggered ability associated with rad counters. This ability has no
 * source and is controlled by the active player. This is an exception to rule 113.8. The
 * full text of this ability is "At the beginning of each player's precombat main phase, if
 * that player has one or more rad counters, that player mills a number of cards equal to
 * the number of rad counters they have. For each nonland card milled this way, that player
 * loses 1 life and removes one rad counter from themselves."
 *
 * @author Susucr
 */
public class RadCounterTriggeredAbility extends TriggeredAbilityImpl {
    public RadCounterTriggeredAbility() {
        super(Zone.ALL, new RadCounterEffect());
        setTriggerPhrase("At the beginning of your precombat main phase, if you have one or more rad counters, ");
    }

    private RadCounterTriggeredAbility(final RadCounterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RadCounterTriggeredAbility copy() {
        return new RadCounterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getControllerId().equals(event.getPlayerId())) {
            return false;
        }
        Player player = game.getPlayer(event.getPlayerId());
        return player != null && player.getCounters().getCount(CounterType.RAD) >= 1;
    }
}

class RadCounterEffect extends OneShotEffect {

    RadCounterEffect() {
        super(Outcome.Neutral);
        staticText = "mill a number of cards equal to the number of rad counters you have. For each nonland "
                + "card milled this way, you lose 1 life and remove one rad counter from you.";
    }

    private RadCounterEffect(final RadCounterEffect effect) {
        super(effect);
    }

    @Override
    public RadCounterEffect copy() {
        return new RadCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = player.getCounters().getCount(CounterType.RAD);
        Cards milled = player.millCards(amount, source, game);
        int countNonLand = milled.count(StaticFilters.FILTER_CARD_NON_LAND, player.getId(), source, game);
        if (countNonLand > 0) {
            // TODO: support gaining life instead with [[Strong, the Brutish Thespian]]
            player.loseLife(countNonLand, game, source, false);
            player.removeCounters(CounterType.RAD.getName(), countNonLand, source, game);
        }
        return true;
    }
}