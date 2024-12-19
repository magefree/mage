package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
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

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that player") + " draws " +
                CardUtil.numberToText(cardsToDraw, "a") + " card" + (cardsToDraw > 1 ? "s" : "") +
                ", then discards " + CardUtil.numberToText(cardsToDiscard, "a") +
                " card" + (cardsToDiscard > 1 ? "s" : "") + (random ? " at random" : "");
    }

}
