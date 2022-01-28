package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Pronoun;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ExileAndReturnTransformedSourceEffect extends OneShotEffect {

    protected Effect additionalEffect;
    protected boolean returnUnderYourControl;

    public ExileAndReturnTransformedSourceEffect() {
        this(Pronoun.IT);
    }

    public ExileAndReturnTransformedSourceEffect(Pronoun pronoun) {
        this(pronoun, null);
    }

    public ExileAndReturnTransformedSourceEffect(Pronoun pronoun, Effect additionalEffect) {
        this(pronoun, additionalEffect, false);
    }

    /**
     * @param pronoun
     * @param additionalEffect       that effect is applies as source is exiled
     * @param returnUnderYourControl return under your or owner control
     */
    public ExileAndReturnTransformedSourceEffect(Pronoun pronoun, Effect additionalEffect, boolean returnUnderYourControl) {
        super(Outcome.Benefit);
        this.additionalEffect = additionalEffect;
        this.returnUnderYourControl = returnUnderYourControl;
        this.staticText = "exile {this}, then return " + pronoun.getObjective()
                + " to the battlefield transformed under " + pronoun.getPossessive()
                + " " + (this.returnUnderYourControl ? "your" : "owner's") + " control";
    }

    public ExileAndReturnTransformedSourceEffect(final ExileAndReturnTransformedSourceEffect effect) {
        super(effect);
        this.additionalEffect = effect.additionalEffect;
        this.returnUnderYourControl = effect.returnUnderYourControl;
    }

    @Override
    public ExileAndReturnTransformedSourceEffect copy() {
        return new ExileAndReturnTransformedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Creature has to be on the battlefield to get exiled and be able to return transformed
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null && sourceObject.getZoneChangeCounter(game) == source.getSourceObjectZoneChangeCounter()) {
            if (controller.moveCards(sourceObject, Zone.EXILED, source, game)) {
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
                Card cardFromExile = game.getCard(source.getSourceId());
                if (cardFromExile != null) {
                    controller.moveCards(cardFromExile, Zone.BATTLEFIELD, source, game, false, false, !this.returnUnderYourControl, null);
                    if (additionalEffect != null) {
                        if (additionalEffect instanceof ContinuousEffect) {
                            game.addEffect((ContinuousEffect) additionalEffect, source);
                        } else {
                            additionalEffect.apply(game, source);
                        }
                    }
                }
            }
        }
        return true;
    }
}
