package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class DrawDiscardTargetEffect extends OneShotEffect {

    private final int cardsToDraw;
    private final int cardsToDiscard;
    private final boolean random;

    public DrawDiscardTargetEffect(int cardsToDraw, int cardsToDiscard) {
        this(cardsToDraw, cardsToDiscard, false);
    }

    public DrawDiscardTargetEffect(int cardsToDraw, int cardsToDiscard, boolean random) {
        super(Outcome.DrawCard);
        this.cardsToDraw = cardsToDraw;
        this.cardsToDiscard = cardsToDiscard;
        this.random = random;
        staticText = new StringBuilder("target player draws ")
                .append(CardUtil.numberToText(cardsToDraw, "a"))
                .append(" card")
                .append(cardsToDraw > 1 ? "s" : "")
                .append(", then discards ")
                .append(CardUtil.numberToText(cardsToDiscard, "a"))
                .append(" card")
                .append(cardsToDiscard > 1 ? "s" : "")
                .append(random ? "at random" : "")
                .toString();
    }

    private DrawDiscardTargetEffect(final DrawDiscardTargetEffect effect) {
        super(effect);
        this.cardsToDraw = effect.cardsToDraw;
        this.cardsToDiscard = effect.cardsToDiscard;
        this.random = effect.random;
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
            player.discard(cardsToDiscard, random, false, source, game);
            return true;
        }
        return false;
    }
}
