/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.costs;

import java.util.UUID;
import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.game.Game;
import mage.players.Player;
import mage.target.Targets;

/**
 *
 * @author LevelX2
 */


public class OrCost implements Cost {
    private Cost firstCost;
    private Cost secondCost;
    private String description;
    // which cost was slected to pay
    private Cost selectedCost;

    public OrCost(Cost firstCost, Cost secondCost, String description) {
        this.firstCost = firstCost;
        this.secondCost = secondCost;
        this.description = description;
    }

    public OrCost(final OrCost cost) {
        this.firstCost = cost.firstCost.copy();
        this.secondCost = cost.secondCost.copy();
        this.description = cost.description;
        this.selectedCost = cost.selectedCost;
    }

    @Override
    public UUID getId() {
        throw new RuntimeException("Not supported method");
    }

    @Override
    public String getText() {
        return description;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return firstCost.canPay(sourceId, controllerId, game) || secondCost.canPay(sourceId, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        selectedCost = null;
        // if only one can be paid select it
        if (!firstCost.canPay(sourceId, controllerId, game)) {
            selectedCost = secondCost;
        }
        if (!secondCost.canPay(sourceId, controllerId, game)) {
            selectedCost = firstCost;
        }
        // if both can be paid player has to select
        if (selectedCost == null) {
            Player controller = game.getPlayer(controllerId);
            if (controller != null) {
                StringBuilder sb  = new StringBuilder();
                if (firstCost instanceof ManaCost) {
                    sb.append("Pay ");
                }
                sb.append(firstCost.getText()).append("?");
                if (controller.chooseUse(Constants.Outcome.Detriment, sb.toString(), game)) {
                    selectedCost = firstCost;
                } else {
                    selectedCost = secondCost;
                }
            }
        }
        if (selectedCost == null) {
            return false;
        }
        return selectedCost.pay(ability, game, sourceId, controllerId, noMana);

    }

    @Override
    public boolean isPaid() {
        if (selectedCost != null) {
            return selectedCost.isPaid();
        }
        return false;
    }

    @Override
    public void clearPaid() {
        selectedCost = null;
        firstCost.clearPaid();
        secondCost.clearPaid();
    }

    @Override
    public void setPaid() {
        if (selectedCost != null) {
            selectedCost.setPaid();
        }
    }

    @Override
    public Targets getTargets() {
        if (selectedCost != null) {
            return selectedCost.getTargets();
        }
        return null;
    }

    @Override
    public Cost copy() {
        return new OrCost(this);
    }
}
