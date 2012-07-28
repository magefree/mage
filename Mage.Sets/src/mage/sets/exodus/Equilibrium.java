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
package mage.sets.exodus;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.SpellCastTriggeredAbility;

/**
 *
 * @author jeffwadsworth
 */
public class Equilibrium extends CardImpl<Equilibrium> {
    
    private static final FilterSpell filter = new FilterSpell("a creature spell");
    
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public Equilibrium(UUID ownerId) {
        super(ownerId, 32, "Equilibrium", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");
        this.expansionSetCode = "EXO";

        this.color.setBlue(true);

        // Whenever you cast a creature spell, you may pay {1}. If you do, return target creature to its owner's hand.
        Ability ability = new SpellCastTriggeredAbility(new DoIfCostPaid(new ReturnToHandTargetEffect(), new GenericManaCost(1)), filter, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public Equilibrium(final Equilibrium card) {
        super(card);
    }

    @Override
    public Equilibrium copy() {
        return new Equilibrium(this);
    }
}
