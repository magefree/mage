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

package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DrawCardAllEffect extends OneShotEffect<DrawCardAllEffect> {

    private TargetController targetController;
    protected DynamicValue amount;

    public DrawCardAllEffect(int amount) {
        this(amount, TargetController.ANY);
    }
    
    public DrawCardAllEffect(DynamicValue amount) {
        this(amount, TargetController.ANY);
    }

    public DrawCardAllEffect(int amount, TargetController targetController) {
        this(new StaticValue(amount), targetController);
    }

    public DrawCardAllEffect(DynamicValue amount, TargetController targetController) {
        super(Outcome.DrawCard);
        this.amount = amount;
        this.targetController = targetController;
        staticText = setText();
    }

    public DrawCardAllEffect(final DrawCardAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.targetController = effect.targetController;
    }

    @Override
    public DrawCardAllEffect copy() {
        return new DrawCardAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        switch(targetController) {
            case ANY:
                for (UUID playerId: sourcePlayer.getInRange()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(amount.calculate(game, source), game);
                    }
                }
                break;
            case OPPONENT:
                for (UUID playerId: game.getOpponents(sourcePlayer.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(amount.calculate(game, source), game);
                    }
                }                
                break;
        }
        return true;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Each ");
        switch(targetController) {
            case ANY:
                sb.append("player");
                break;
            case OPPONENT:
                sb.append("opponent");
                break;
            default:
                throw new UnsupportedOperationException("Not supported value for targetController");
        }
        sb.append(" draws ");
        sb.append(CardUtil.numberToText(amount.toString(),"a"));
        sb.append(" card");
        sb.append(amount.toString().equals("1")?"":"s");
        return sb.toString();
    }
}
