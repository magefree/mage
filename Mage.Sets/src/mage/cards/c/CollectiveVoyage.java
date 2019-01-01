
package mage.cards.c;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class CollectiveVoyage extends CardImpl {

    public CollectiveVoyage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Join forces - Starting with you, each player may pay any amount of mana. Each player searches their library for up to X basic land cards, where X is the total amount of mana paid this way, puts them onto the battlefield tapped, then shuffles their library.
        this.getSpellAbility().addEffect(new CollectiveVoyageEffect());
    }

    public CollectiveVoyage(final CollectiveVoyage card) {
        super(card);
    }

    @Override
    public CollectiveVoyage copy() {
        return new CollectiveVoyage(this);
    }
}

class CollectiveVoyageEffect extends OneShotEffect {

    public CollectiveVoyageEffect() {
        super(Outcome.Detriment);
        this.staticText = "<i>Join forces</i> &mdash; Starting with you, each player may pay any amount of mana. Each player searches their library for up to X basic land cards, where X is the total amount of mana paid this way, puts them onto the battlefield tapped, then shuffles their library";
    }

    public CollectiveVoyageEffect(final CollectiveVoyageEffect effect) {
        super(effect);
    }

    @Override
    public CollectiveVoyageEffect copy() {
        return new CollectiveVoyageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xSum = 0;
            xSum += playerPaysXGenericMana(controller, source, game);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!Objects.equals(playerId, controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        xSum += playerPaysXGenericMana(player, source, game);

                    }
                }
            }
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, xSum, StaticFilters.FILTER_CARD_BASIC_LAND);
                    if (player.searchLibrary(target, game)) {
                        player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, true, null);
                        player.shuffleLibrary(source, game);
                    }

                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }

    protected static int playerPaysXGenericMana(Player player, Ability source, Game game) {
        int xValue = 0;
        boolean payed = false;
        while (player.canRespond() && !payed) {
            int bookmark = game.bookmarkState();
            player.resetStoredBookmark(game);
            xValue = player.announceXMana(0, Integer.MAX_VALUE, "How much mana will you pay?", game, source);
            if (xValue > 0) {
                Cost cost = new GenericManaCost(xValue);
                payed = cost.pay(source, game, source.getSourceId(), player.getId(), false, null);
            } else {
                payed = true;
            }
            if (!payed) {
                game.restoreState(bookmark, "Collective Voyage");
                game.fireUpdatePlayersEvent();
            } else {
                game.removeBookmark(bookmark);
            }
        }
        game.informPlayers(player.getLogName() + " pays {" + xValue + "}.");
        return xValue;
    }
}
