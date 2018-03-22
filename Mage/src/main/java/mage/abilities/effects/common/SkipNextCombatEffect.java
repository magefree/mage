/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SkipNextCombatEffect extends OneShotEffect {
    
    public SkipNextCombatEffect() {
        super(Outcome.Detriment);
        staticText = "target opponent skips their next combat phase";
    }

    public SkipNextCombatEffect(SkipNextCombatEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (targetPointer != null) {
            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) {
                game.getState().getTurnMods().add(new TurnMod(player.getId(), TurnPhase.COMBAT, null, true));
                return true;
            }
        }
        return false;
    }

    @Override
    public SkipNextCombatEffect copy() {
        return new SkipNextCombatEffect();
    }
}
