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
package mage.sets.jacevschandra;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ReturnToHandTargetPermanentCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public class Gush extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Island");
    static{
        filter.add(new SubtypePredicate("Island"));
    }
    public Gush(UUID ownerId) {
        super(ownerId, 27, "Gush", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{U}");
        this.expansionSetCode = "DD2";


        // You may return two Islands you control to their owner's hand rather than pay Gush's mana cost.
        AlternativeCostSourceAbility ability;   
        ability = new AlternativeCostSourceAbility(new ReturnToHandTargetPermanentCost(new TargetControlledPermanent(2, 2, filter, true)));
        this.addAbility(ability);
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    public Gush(final Gush card) {
        super(card);
    }

    @Override
    public Gush copy() {
        return new Gush(this);
    }
}
