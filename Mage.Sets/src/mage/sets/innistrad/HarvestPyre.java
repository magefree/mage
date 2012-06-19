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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public class HarvestPyre extends CardImpl<HarvestPyre> {

    public HarvestPyre(UUID ownerId) {
        super(ownerId, 146, "Harvest Pyre", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "ISD";

        this.color.setRed(true);

        // As an additional cost to cast Harvest Pyre, exile X cards from your graveyard.
        this.getSpellAbility().addCost(new HarvestPyreCost());

        // Harvest Pyre deals X damage to target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(new GetXValue()));
    }

    public HarvestPyre(final HarvestPyre card) {
        super(card);
    }

    @Override
    public HarvestPyre copy() {
        return new HarvestPyre(this);
    }
}

class HarvestPyreCost extends CostImpl<HarvestPyreCost> implements VariableCost  {

    protected int amountPaid = 0;

    public HarvestPyreCost() {
        this.text = "As an additional cost to cast Harvest Pyre, exile X cards from your graveyard";
    }

    public HarvestPyreCost(final HarvestPyreCost cost) {
        super(cost);
        this.amountPaid = cost.amountPaid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        amountPaid = 0;
        Target target = new TargetCardInYourGraveyard();
        Player player = game.getPlayer(controllerId);
        while (true) {
            target.clearChosen();
            if (target.canChoose(controllerId, game) && target.choose(Outcome.Exile, controllerId, sourceId, game)) {
                Card card = player.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    player.getGraveyard().remove(card);
                    card.moveToExile(null, "", sourceId, game);
                    amountPaid++;
                }
            }
            else 
                break;
        }
        paid = true;
        return true;
    }

    @Override
    public int getAmount() {
        return amountPaid;
    }

    @Override
    public void setFilter(FilterMana filter) {
    }

    @Override
    public HarvestPyreCost copy() {
        return new HarvestPyreCost(this);
    }

    @Override
    public void setAmount(int amount) {
        amountPaid = amount;
    }

}
