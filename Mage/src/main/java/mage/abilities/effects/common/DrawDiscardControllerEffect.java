package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DrawDiscardControllerEffect extends OneShotEffect {

    private final int cardsToDraw;
    private final int cardsToDiscard;
    private final boolean optional;

    public DrawDiscardControllerEffect() {
        this(1, 1);
    }

    public DrawDiscardControllerEffect(boolean optional) {
        this(1, 1, optional);
    }

    public DrawDiscardControllerEffect(int cardsToDraw, int cardsToDiscard) {
        this(cardsToDraw, cardsToDiscard, false);
    }

    public DrawDiscardControllerEffect(int cardsToDraw, int cardsToDiscard, boolean optional) {
        super(Outcome.DrawCard);
        this.cardsToDraw = cardsToDraw;
        this.cardsToDiscard = cardsToDiscard;
        this.optional = optional;
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
        if (player == null) {
            return false;
        }
        if (optional && !player.chooseUse(outcome, "Draw, then discard?", source, game)) {
            return true;
        }
        player.drawCards(cardsToDraw, source, game);
        player.discard(cardsToDiscard, false, false, source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return (optional ? "you may " : "") +
                "draw " +
                (cardsToDraw == 1 ? "a" : CardUtil.numberToText(cardsToDraw)) +
                " card" +
                (cardsToDraw == 1 ? "" : "s") +
                (optional ? ". If you do," : ", then") +
                " discard " +
                (cardsToDiscard == 1 ? "a" : CardUtil.numberToText(cardsToDiscard)) +
                " card" +
                (cardsToDiscard == 1 ? "" : "s");
    }
}
