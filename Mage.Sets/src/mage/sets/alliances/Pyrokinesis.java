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
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author Plopman
 */
public class Pyrokinesis extends CardImpl<Pyrokinesis> {

    public Pyrokinesis(UUID ownerId) {
        super(ownerId, 115, "Pyrokinesis", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{R}{R}");
        this.expansionSetCode = "ALL";

        this.color.setRed(true);

        // You may exile a red card from your hand rather than pay Pyrokinesis's mana cost.
        FilterOwnedCard filter = new FilterOwnedCard("a red card from your hand");
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(Predicates.not(new CardIdPredicate(this.getId()))); // the exile cost can never be paid with the card itself
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));
        
        // Pyrokinesis deals 4 damage divided as you choose among any number of target creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(4));
    }

    public Pyrokinesis(final Pyrokinesis card) {
        super(card);
    }

    @Override
    public Pyrokinesis copy() {
        return new Pyrokinesis(this);
    }
}
