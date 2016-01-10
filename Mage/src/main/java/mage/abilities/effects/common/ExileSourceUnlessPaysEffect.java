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
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * 
 * @author fireshoes
 */
public class ExileSourceUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;

    public ExileSourceUnlessPaysEffect(Cost cost) {
        super(Outcome.Exile);
        this.cost = cost;
    }

    public ExileSourceUnlessPaysEffect(final ExileSourceUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            StringBuilder sb = new StringBuilder(cost.getText()).append("?");
            if (!sb.toString().toLowerCase().startsWith("exile ") && !sb.toString().toLowerCase().startsWith("return ")) {
                sb.insert(0, "Pay ");
            }
            String message = CardUtil.replaceSourceName(sb.toString(), sourcePermanent.getLogName());
            message = Character.toUpperCase(message.charAt(0)) + message.substring(1);
            if (controller.chooseUse(Outcome.Benefit, message, source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    return true;
                }
            }
            controller.moveCardsToExile(sourcePermanent, source, game, true, null, "");
            return true;
        }
        return false;
    }

    @Override
    public ExileSourceUnlessPaysEffect copy() {
        return new ExileSourceUnlessPaysEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        StringBuilder sb = new StringBuilder("exile {this} unless you ");
        String costText = cost.getText();
        if (costText.toLowerCase().startsWith("discard")
                || costText.toLowerCase().startsWith("remove")
                || costText.toLowerCase().startsWith("return")
                || costText.toLowerCase().startsWith("exile")
                || costText.toLowerCase().startsWith("sacrifice")) {
            sb.append(costText.substring(0, 1).toLowerCase());
            sb.append(costText.substring(1));
        } else {
            sb.append("pay ").append(costText);
        }

        return sb.toString();
    }
}
