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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterMana;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class DevastatingSummons extends CardImpl<DevastatingSummons> {

    public DevastatingSummons(UUID ownerId) {
        super(ownerId, 140, "Devastating Summons", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "ROE";

        this.color.setRed(true);

        // As an additional cost to cast Devastating Summons, sacrifice X lands.
        this.getSpellAbility().addCost(new DevastatingSummonsCost());
        
        // Put two X/X red Elemental creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new DevastatingSummonsEffect());
    }

    public DevastatingSummons(final DevastatingSummons card) {
        super(card);
    }

    @Override
    public DevastatingSummons copy() {
        return new DevastatingSummons(this);
    }
}

class DevastatingSummonsCost extends CostImpl<DevastatingSummonsCost> implements VariableCost  {

    protected int amountPaid = 0;

    public DevastatingSummonsCost() {
        this.text = "sacrifice X lands.";
    }

    public DevastatingSummonsCost(final DevastatingSummonsCost cost) {
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
        FilterLandPermanent filter = new FilterLandPermanent("X number of lands you control.");
        TargetLandPermanent target = new TargetLandPermanent(filter);
        while (true) {
            target.clearChosen();
            if (target.canChoose(controllerId, game) && target.choose(Constants.Outcome.Sacrifice, controllerId, sourceId, game)) {
                UUID land = target.getFirstTarget();
                if (land != null) {
                    game.getPermanent(land).sacrifice(sourceId, game);
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
    public DevastatingSummonsCost copy() {
        return new DevastatingSummonsCost(this);
    }

    @Override
    public void setAmount(int amount) {
        amountPaid = amount;
    }
}

class DevastatingSummonsEffect extends OneShotEffect<DevastatingSummonsEffect> {

    public DevastatingSummonsEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        staticText = "Put two X/X red Elemental creature tokens onto the battlefield";
    }

    public DevastatingSummonsEffect(final DevastatingSummonsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ElementalToken token = new ElementalToken();
        
        token.getPower().setValue(new GetXValue().calculate(game, source));
        token.getToughness().setValue(new GetXValue().calculate(game, source));
        
        token.putOntoBattlefield(2, game, source.getSourceId(), source.getControllerId());
        
        return true;
    }

    @Override
    public DevastatingSummonsEffect copy() {
        return new DevastatingSummonsEffect(this);
    }
}

class ElementalToken extends Token {

    public ElementalToken() {
        super("Elemental", "X/X red Elemental creature");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.RED;
        subtype.add("Elemental");
    }
}
