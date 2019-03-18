
package mage.cards.m;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class MindsAglow extends CardImpl {

    public MindsAglow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Join forces - Starting with you, each player may pay any amount of mana. Each player draws X cards, where X is the total amount of mana paid this way.
        this.getSpellAbility().addEffect(new MindsAglowEffect());

    }

    public MindsAglow(final MindsAglow card) {
        super(card);
    }

    @Override
    public MindsAglow copy() {
        return new MindsAglow(this);
    }
}

class MindsAglowEffect extends OneShotEffect {

    public MindsAglowEffect() {
        super(Outcome.Detriment);
        this.staticText = "<i>Join forces</i> &mdash; Starting with you, each player may pay any amount of mana. Each player draws X cards, where X is the total amount of mana paid this way";
    }

    public MindsAglowEffect(final MindsAglowEffect effect) {
        super(effect);
    }

    @Override
    public MindsAglowEffect copy() {
        return new MindsAglowEffect(this);
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
            if (xSum > 0) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(xSum, game);
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
            xValue = player.announceXMana(0, Integer.MAX_VALUE, "How much mana will you pay?", game, source);
            if (xValue > 0) {
                Cost cost = new GenericManaCost(xValue);
                payed = cost.pay(source, game, source.getSourceId(), player.getId(), false, null);
                if (!payed) {
                    game.undo(player.getId());
                }
            } else {
                payed = true;
            }
        }
        game.informPlayers(player.getLogName() + " pays {" + xValue + "}.");
        return xValue;
    }
}
