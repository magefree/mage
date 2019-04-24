/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth
 */

public class SkipNextDrawStepTargetEffect extends ReplacementEffectImpl {

    public SkipNextDrawStepTargetEffect() {
        super(Duration.OneUse, Outcome.Detriment);
        staticText = "Target player skips their next draw step";
    }

    public SkipNextDrawStepTargetEffect(final SkipNextDrawStepTargetEffect effect) {
        super(effect);
    }

    @Override
    public SkipNextDrawStepTargetEffect copy() {
        return new SkipNextDrawStepTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getFirstTarget());
    }
}