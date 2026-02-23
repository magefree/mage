package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class StepBetweenWorlds extends CardImpl {

    public StepBetweenWorlds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Each player may shuffle their hand and graveyard into their library. Each player who does draws seven cards. Exile Step Between Worlds.
        this.getSpellAbility().addEffect(new StepBetweenWorldsEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());

        // Plot {4}{U}{U}
        this.addAbility(new PlotAbility("{4}{U}{U}"));
    }

    private StepBetweenWorlds(final StepBetweenWorlds card) {
        super(card);
    }

    @Override
    public StepBetweenWorlds copy() {
        return new StepBetweenWorlds(this);
    }
}

class StepBetweenWorldsEffect extends OneShotEffect {

    StepBetweenWorldsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each player may shuffle their hand and graveyard into their library. Each player who does draws seven cards";
    }

    private StepBetweenWorldsEffect(final StepBetweenWorldsEffect effect) {
        super(effect);
    }

    @Override
    public StepBetweenWorldsEffect copy() {
        return new StepBetweenWorldsEffect(this);
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
