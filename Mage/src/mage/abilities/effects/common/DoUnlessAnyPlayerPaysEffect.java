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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class DoUnlessAnyPlayerPaysEffect extends OneShotEffect {
    protected Effects executingEffects = new Effects();
    private final Cost cost;
    private String chooseUseText;

    public DoUnlessAnyPlayerPaysEffect(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoUnlessAnyPlayerPaysEffect(Effect effect, Cost cost, String chooseUseText) {
        super(Outcome.Benefit);
        this.executingEffects.add(effect);
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoUnlessAnyPlayerPaysEffect(final DoUnlessAnyPlayerPaysEffect effect) {
        super(effect);
        this.executingEffects = effect.executingEffects.copy();
        this.cost = effect.cost.copy();
        this.chooseUseText = effect.chooseUseText;
    }

    public void addEffect(Effect effect) {
        executingEffects.add(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            String message;
            if (chooseUseText == null) {
                String effectText = executingEffects.getText(source.getModes().getMode());
                message = "Pay " + cost.getText() + " to prevent (" + effectText.substring(0, effectText.length() -1) + ")?";
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, sourceObject.getLogName());
            boolean result = true;
            boolean doEffect = true;
            // check if any player is willing to pay
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null && cost.canPay(source, source.getSourceId(), player.getId(), game) && player.chooseUse(Outcome.Detriment, message, game)) {
                    cost.clearPaid();
                    if (cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                        if (!game.isSimulation())
                            game.informPlayers(player.getName() + " pays the cost to prevent the effect");
                        doEffect = false;
                        break;
                    }
                }
            }
            // do the effects if nobody paid
            if (doEffect) {
                for(Effect effect: executingEffects) {
                    effect.setTargetPointer(this.targetPointer);
                    if (effect instanceof OneShotEffect) {
                        result &= effect.apply(game, source);
                    }
                    else {
                        game.addEffect((ContinuousEffect) effect, source);
                    }
                }
            }
            return result;
        }
        return false;
    }

    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        String effectsText = executingEffects.getText(mode);
        return  effectsText.substring(0, effectsText.length() -1) + " unless any player pays " + cost.getText();
    }

    @Override
    public DoUnlessAnyPlayerPaysEffect copy() {
        return new DoUnlessAnyPlayerPaysEffect(this);
    }
}