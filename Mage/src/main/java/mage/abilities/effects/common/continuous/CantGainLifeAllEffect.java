/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common.continuous;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class CantGainLifeAllEffect extends ContinuousEffectImpl {
 
    private TargetController targetController;
    
    public CantGainLifeAllEffect() {
        this(Duration.WhileOnBattlefield);
    }
    
    public CantGainLifeAllEffect(Duration duration) {
        this(duration, TargetController.ANY);
    }

    public CantGainLifeAllEffect(Duration duration, TargetController targetController) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.targetController = targetController;
        staticText = setText();
        
    }

    public CantGainLifeAllEffect(final CantGainLifeAllEffect effect) {
        super(effect);
        this.targetController = effect.targetController;
    }

    @Override
    public CantGainLifeAllEffect copy() {
        return new CantGainLifeAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {        
            switch (targetController) {
                case YOU:
                    controller.setCanGainLife(false);
                break;
                case NOT_YOU:
                    for (UUID playerId: controller.getInRange()) {
                        Player player = game.getPlayer(playerId);
                        if (player != null && !player.equals(controller)) {
                            player.setCanGainLife(false);
                        }
                    }
                break;
                case OPPONENT:
                    for (UUID playerId: controller.getInRange()) {
                        if (controller.hasOpponent(playerId, game)) {
                            Player player = game.getPlayer(playerId);
                            if (player != null) {
                                player.setCanGainLife(false);
                            }
                        }
                    }
                break;
                case ANY:
                    for (UUID playerId: controller.getInRange()) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            player.setCanGainLife(false);
                        }
                    }
                break;
            }
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case YOU:
                sb.append("You");
            break;
            case NOT_YOU:
                sb.append("Other players");
            break;
            case OPPONENT:
                sb.append("Your opponents");
            break;
            case ANY:
                sb.append("Players");
            break;
        }        
        sb.append(" can't gain life");
        if (!this.duration.toString().isEmpty()) {
            sb.append(" ");
            if (duration.equals(Duration.EndOfTurn)) {
                sb.append("this turn");
            } else {
                sb.append(duration.toString());
            }
            
        }
        return sb.toString();        
    }
}
