package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurtlesInTime extends CardImpl {

    public TurtlesInTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // Return all creatures to their owners' hands. Each player may shuffle their hand and graveyard into their library, then each player who does draws seven cards.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().addEffect(new TurtlesInTimeEffect());

        // Exile Turtles in Time.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private TurtlesInTime(final TurtlesInTime card) {
        super(card);
    }

    @Override
    public TurtlesInTime copy() {
        return new TurtlesInTime(this);
    }
}

class TurtlesInTimeEffect extends OneShotEffect {

    TurtlesInTimeEffect() {
        super(Outcome.Benefit);
        staticText = "Each player may shuffle their hand and graveyard into their library, " +
                "then each player who does draws seven cards";
    }

    private TurtlesInTimeEffect(final TurtlesInTimeEffect effect) {
        super(effect);
    }

    @Override
    public TurtlesInTimeEffect copy() {
        return new TurtlesInTimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Player> players = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (player.chooseUse(Outcome.DrawCard, "Shuffle your hand and graveyard into your library and draw seven cards?", source, game)) {
                game.informPlayers(player.getLogName() + " chooses to shuffle and draw");
                players.add(player);
            } else {
                game.informPlayers(player.getLogName() + " chooses not to shuffle and draw");
            }
        }
        for (Player player : players) {
            Cards cards = new CardsImpl(player.getHand());
            cards.addAll(player.getGraveyard());
            player.shuffleCardsToLibrary(cards, game, source);
            player.drawCards(7, source, game);
        }
        return !players.isEmpty();
    }
}
