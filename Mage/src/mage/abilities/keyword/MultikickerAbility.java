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

package mage.abilities.keyword;

import mage.abilities.costs.mana.KickerManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MultikickerAbility extends KickerAbility {

    int activateCount;

    public MultikickerAbility(Effect effect, boolean replaces) {
        super(new KickerManaCost("{1}"));
     //        super(effect, replaces);

    }

    public MultikickerAbility(ManaCosts manaCosts) {
        super((KickerManaCost) manaCosts);
        //super(new EmptyEffect(""), false);
        this.addManaCost(manaCosts);
    }

    public MultikickerAbility(final MultikickerAbility ability) {
        super(ability);
        this.activateCount = ability.activateCount;
    }

    @Override
    public MultikickerAbility copy() {
        return new MultikickerAbility(this);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        activateCount = 0;
        while (true) {
            this.costs.clearPaid();
            this.manaCostsToPay.clearPaid();
            if (!super.activate(game, noMana))
                break;
            activateCount++;
        }
//        kicked = activateCount > 0;
//        return kicked;
        return false;
    }

    @Override
    public boolean resolve(Game game) {
        boolean result = false;
        for (int i = 0; i < activateCount; i++) {
            result |= super.resolve(game);
        }
        return result;
    }

//    @Override
//    public String getKickerText(boolean withRemainder) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Multikicker ");
//        if (manaCosts.size() > 0) {
//            sb.append(manaCosts.getText());
//            if (costs.size() > 0) {
//                sb.append(",");
//            }
//        }
//        if (costs.size() > 0) {
//            sb.append(costs.getText());
//        }
//        if (withRemainder) {
//            sb.append("  (You may pay an additional ").append(manaCosts.getText()).append(" any number of times as you cast this spell.)");
//        }
//        return sb.toString();
//    }

    public int getActivateCount() {
        return activateCount;
    }
}
