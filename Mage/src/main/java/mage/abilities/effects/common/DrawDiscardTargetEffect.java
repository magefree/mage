

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class DrawDiscardTargetEffect extends OneShotEffect {

    private int cardsToDraw;
    private int cardsToDiscard;

    public DrawDiscardTargetEffect() {
        this(1,1);
    }

    public DrawDiscardTargetEffect(int cardsToDraw, int cardsToDiscard) {
        super(Outcome.DrawCard);
        this.cardsToDraw = cardsToDraw;
        this.cardsToDiscard = cardsToDiscard;
        staticText = new StringBuilder("Target player draws ")
                .append(cardsToDraw == 1?"a": CardUtil.numberToText(cardsToDraw))
                .append(" card").append(cardsToDraw == 1?"": "s")
                .append(", then discards ")
                .append(cardsToDiscard == 1?"a": CardUtil.numberToText(cardsToDiscard))
                .append(" card").append(cardsToDiscard == 1?"": "s").toString();
    }

    public DrawDiscardTargetEffect(final DrawDiscardTargetEffect effect) {
        super(effect);
        this.cardsToDraw = effect.cardsToDraw;
        this.cardsToDiscard = effect.cardsToDiscard;
    }

    @Override
    public DrawDiscardTargetEffect copy() {
        return new DrawDiscardTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.drawCards(cardsToDraw, source, game);
            player.discard(cardsToDiscard, false, false, source, game);
            return true;
        }
        return false;
    }

}
