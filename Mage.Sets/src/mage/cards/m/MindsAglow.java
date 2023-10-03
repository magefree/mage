package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MindsAglow extends CardImpl {

    public MindsAglow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Join forces - Starting with you, each player may pay any amount of mana. Each player draws X cards, where X is the total amount of mana paid this way.
        this.getSpellAbility().addEffect(new MindsAglowEffect());

    }

    private MindsAglow(final MindsAglow card) {
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

    private MindsAglowEffect(final MindsAglowEffect effect) {
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
            xSum += ManaUtil.playerPaysXGenericMana(false, "Minds Aglow", controller, source, game);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!Objects.equals(playerId, controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        xSum += ManaUtil.playerPaysXGenericMana(false, "Minds Aglow", player, source, game);
                    }
                }
            }
            if (xSum > 0) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(xSum, source, game);
                    }
                }

            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
