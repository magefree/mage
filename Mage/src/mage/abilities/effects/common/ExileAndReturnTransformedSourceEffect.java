/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ExileAndReturnTransformedSourceEffect extends OneShotEffect {

    public static enum Gender {

        MALE, FEMAL
    };

    protected Effect additionalEffect;

    public ExileAndReturnTransformedSourceEffect(Gender gender) {
        this(gender, null);
    }

    /**
     * @param gender
     * @param additionalEffect that effect is applies as source is exiled
     */
    public ExileAndReturnTransformedSourceEffect(Gender gender, Effect additionalEffect) {
        super(Outcome.Benefit);
        this.additionalEffect = additionalEffect;
        this.staticText = "exile {this}, then return " + (gender.equals(Gender.MALE) ? "him" : "her")
                + " to the battlefield transformed under" + (gender.equals(Gender.MALE) ? "his" : "her") + " owner's control";
    }

    public ExileAndReturnTransformedSourceEffect(final ExileAndReturnTransformedSourceEffect effect) {
        super(effect);
        this.additionalEffect = effect.additionalEffect;
    }

    @Override
    public ExileAndReturnTransformedSourceEffect copy() {
        return new ExileAndReturnTransformedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null) {
            Card card = (Card) sourceObject;
            if (controller.moveCards(card, Zone.BATTLEFIELD, Zone.EXILED, source, game)) {
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
                controller.putOntoBattlefieldWithInfo(card, game, Zone.EXILED, source.getSourceId());
                if (additionalEffect != null) {
                    if (additionalEffect instanceof ContinuousEffect) {
                        game.addEffect((ContinuousEffect) additionalEffect, source);
                    } else {
                        additionalEffect.apply(game, source);
                    }
                }
            }
        }
        return true;
    }
}
