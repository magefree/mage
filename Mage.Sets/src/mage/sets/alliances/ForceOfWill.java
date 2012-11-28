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
package mage.sets.alliances;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Plopman
 */
public class ForceOfWill extends CardImpl<ForceOfWill> {

    public ForceOfWill(UUID ownerId) {
        super(ownerId, 42, "Force of Will", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");
        this.expansionSetCode = "ALL";

        this.color.setBlue(true);

        // You may pay 1 life and exile a blue card from your hand rather than pay Force of Will's mana cost.
        FilterOwnedCard filter = new FilterOwnedCard("blue card from your hand");
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(Predicates.not(new CardIdPredicate(this.getId()))); // the exile cost can never be paid with the card itself
        CostsImpl<Cost> costs = new CostsImpl<Cost>();
        costs.add(new PayLifeCost(1));
        costs.add(new ExileFromHandCost(new TargetCardInHand(filter)));
        this.getSpellAbility().addAlternativeCost(new AlternativeCostImpl("Pay 1 life and exile a blue card from your hand rather than pay Force of Will's mana cost", costs));
        
        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public ForceOfWill(final ForceOfWill card) {
        super(card);
    }

    @Override
    public ForceOfWill copy() {
        return new ForceOfWill(this);
    }
}
