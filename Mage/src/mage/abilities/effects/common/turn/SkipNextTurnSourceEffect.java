/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.turn;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;

/**
 *
 * @author Mael
 */
public class SkipNextTurnSourceEffect extends OneShotEffect {
    
    public SkipNextTurnSourceEffect() {
        super(Outcome.Neutral);
        staticText = "You skip your next turn";
    }

    public SkipNextTurnSourceEffect(final SkipNextTurnSourceEffect effect) {
        super(effect);
    }

    @Override
    public SkipNextTurnSourceEffect copy() {
        return new SkipNextTurnSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), true));
        return true;
    }
}
