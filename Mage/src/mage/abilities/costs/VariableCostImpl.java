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
import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;

/**
 *
 * @author LevelX2
 * @param <T> variable cost type
 */

public abstract class VariableCostImpl<T extends VariableCostImpl<T>> implements Cost, VariableCost {

    protected UUID id;
    protected String text;
    protected boolean paid;
    protected Targets targets;
    protected int amountPaid;
    protected String xText;
    protected String actionText;

    @Override
    public abstract T copy();

    public VariableCostImpl(String actionText) {
        this("X", actionText);
    }
    /**
     *
     * @param xText string for the defined value
     * @param actionText what happens with the value (e.g. "to tap", "to exile from your graveyard")
     */
    public VariableCostImpl(String xText, String actionText) {
        id = UUID.randomUUID();
        paid = false;
        targets = new Targets();
        amountPaid = 0;
        this.xText = xText;
        this.actionText = actionText;
    }

    public VariableCostImpl(final VariableCostImpl cost) {
        this.id = cost.id;
        this.text = cost.text;
        this.paid = cost.paid;
        this.targets = cost.targets.copy();
        this.xText = cost.xText;
        this.actionText = cost.actionText;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getActionText() {
        return actionText;
    }

    public void addTarget(Target target) {
        if (target != null) {
            this.targets.add(target);
        }
    }

    @Override
    public Targets getTargets() {
        return this.targets;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public void clearPaid() {
        paid = false;
        amountPaid = 0;
    }

    @Override
    public void setPaid() {
        paid = true;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true; /* not used */
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        return true; /* not used */
    }

    @Override
    public int getAmount() {
        return amountPaid;
    }

    @Override
    public void setAmount(int amount) {
        amountPaid = amount;
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        return 0;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int announceXValue(Ability source, Game game) {
        int xValue = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            xValue = controller.announceXCost(0, getMaxValue(source, game),
                    new StringBuilder("Announce the number of ").append(actionText).toString(),
                    game, source, this);
        }
        return xValue;
    }
}
