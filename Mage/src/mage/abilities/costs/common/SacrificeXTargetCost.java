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

package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class SacrificeXTargetCost extends VariableCostImpl<SacrificeXTargetCost>  {

    protected FilterControlledPermanent filter;
    
    public SacrificeXTargetCost(FilterControlledPermanent filter) {
        this(filter, false);
    }

    public SacrificeXTargetCost(FilterControlledPermanent filter, boolean additionalCostText) {
        super(new StringBuilder(filter.getMessage()).append(" to sacrifice").toString());
        this.text = new StringBuilder(additionalCostText ? "As an additional cost to cast {source}, sacrifice ":"Sacrifice ").append(xText).append(" ").append(filter.getMessage()).toString();
        this.filter = filter;
    }

    public SacrificeXTargetCost(final SacrificeXTargetCost cost) {
        super(cost);
        this.filter = cost.filter;
    }

    @Override
    public SacrificeXTargetCost copy() {
        return new SacrificeXTargetCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetControlledPermanent target = new TargetControlledPermanent(xValue, xValue, filter, true);
        target.setRequired(true);
        return new SacrificeTargetCost(target);
    }

}
