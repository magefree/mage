/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class MillThenDrawControllerEffect extends OneShotEffect {

    private final int cardsToMill;
    private final int cardsToDraw;
    private final boolean optional;

    public MillThenDrawControllerEffect() {
        this(1, 1);
    }

    public MillThenDrawControllerEffect(boolean optional) {
        this(1, 1, optional);
    }

    public MillThenDrawControllerEffect(int cardsToMill, int cardsToDraw) {
        this(cardsToMill, cardsToDraw, false);
    }

    public MillThenDrawControllerEffect(int cardsToMill, int cardsToDraw, boolean optional) {
        super(Outcome.Benefit);
        this.cardsToMill = cardsToMill;
        this.cardsToDraw = cardsToDraw;
        this.optional = optional;
    }

    protected MillThenDrawControllerEffect(final MillThenDrawControllerEffect effect) {
        super(effect);
        this.cardsToMill = effect.cardsToMill;
        this.cardsToDraw = effect.cardsToDraw;
        this.optional = effect.optional;
    }

    @Override
    public MillThenDrawControllerEffect copy() {
        return new MillThenDrawControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (optional && !controller.chooseUse(outcome, "Mill, then draw?", source, game)) {
            return true;
        }
        controller.millCards(cardsToDraw, source, game);
        controller.drawCards(cardsToMill, source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null 
                && !staticText.isEmpty()) {
            return staticText;
        }
        return (optional ? "you may " : "") +
                "mill " +
                (cardsToMill == 1 ? "a" : CardUtil.numberToText(cardsToMill)) +
                " card" +
                (cardsToMill == 1 ? "" : "s") +
                (optional ? ". If you do," : ", then") +
                " draw " +
                (cardsToDraw == 1 ? "a" : CardUtil.numberToText(cardsToDraw)) +
                " card" +
                (cardsToDraw == 1 ? "" : "s");
    }
}
