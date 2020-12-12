
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DrawDiscardControllerEffect extends OneShotEffect {

    private int cardsToDraw;
    private int cardsToDiscard;
    private boolean optional;

    public DrawDiscardControllerEffect() {
        this(1, 1);
    }

    public DrawDiscardControllerEffect(int cardsToDraw, int cardsToDiscard) {
        this(cardsToDraw, cardsToDiscard, false);
    }

    public DrawDiscardControllerEffect(int cardsToDraw, int cardsToDiscard, boolean optional) {
        super(Outcome.DrawCard);
        this.cardsToDraw = cardsToDraw;
        this.cardsToDiscard = cardsToDiscard;
        this.optional = optional;
        staticText = new StringBuilder(optional ? "you may " : "")
                .append("draw ")
                .append(cardsToDraw == 1 ? "a" : CardUtil.numberToText(cardsToDraw))
                .append(" card").append(cardsToDraw == 1 ? "" : "s")
                .append(optional ? ", if you do" : ", then")
                .append(" discard ")
                .append(cardsToDiscard == 1 ? "a" : CardUtil.numberToText(cardsToDiscard))
                .append(" card").append(cardsToDiscard == 1 ? "" : "s").toString();
    }

    public DrawDiscardControllerEffect(final DrawDiscardControllerEffect effect) {
        super(effect);
        this.cardsToDraw = effect.cardsToDraw;
        this.cardsToDiscard = effect.cardsToDiscard;
        this.optional = effect.optional;
    }

    @Override
    public DrawDiscardControllerEffect copy() {
        return new DrawDiscardControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!optional || player.chooseUse(outcome, "Use draw, then discard effect?", source, game)) {
                player.drawCards(cardsToDraw, source, game);
                player.discard(cardsToDiscard, false, false, source, game);
            }
            return true;
        }
        return false;
    }

}
