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

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DrawCardTargetEffect extends OneShotEffect<DrawCardTargetEffect> {

    protected DynamicValue amount;
    protected boolean optional;
    protected boolean upTo;

    public DrawCardTargetEffect(int amount) {
        this(new StaticValue(amount));
    }
    public DrawCardTargetEffect(int amount, boolean optional) {
        this(new StaticValue(amount), optional);
    }

    public DrawCardTargetEffect(DynamicValue amount) {
        this(amount, false);
    }

    public DrawCardTargetEffect(DynamicValue amount, boolean optional) {
        this(amount, optional, false);
    }

    public DrawCardTargetEffect(DynamicValue amount, boolean optional, boolean upto) {
        super(Outcome.DrawCard);
        this.amount = amount.copy();
        this.optional = optional;
        this.upTo = upto;
    }

    public DrawCardTargetEffect(final DrawCardTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.optional = effect.optional;
        this.upTo = effect.upTo;
    }

    @Override
    public DrawCardTargetEffect copy() {
        return new DrawCardTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int cardsToDraw = amount.calculate(game, source);
            if (upTo) {
                cardsToDraw = player.getAmount(0, cardsToDraw, "Draw how many cards?", game);
            }
            if (!optional || player.chooseUse(outcome, "Use draw effect?", game)) {
                player.drawCards(cardsToDraw, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (mode.getTargets().size() > 0) {
            sb.append("Target ").append(mode.getTargets().get(0).getTargetName());
        } else {
            sb.append("that player");
        }
        if (optional) {
            sb.append(" may draw ");
        } else {
            sb.append(" draws ");
        }
        if (upTo) {
            sb.append("up to ");
        }
        sb.append(CardUtil.numberToText(amount.toString())).append(" card");
        try {
            if (Integer.parseInt(amount.toString()) > 1) {
                sb.append("s");
            }
        } catch (Exception e) {
            sb.append("s");
        }
        String message = amount.getMessage();
        if (message.length() > 0) {
            sb.append(" for each ");
        }
        sb.append(message);
        return sb.toString();
    }


}
