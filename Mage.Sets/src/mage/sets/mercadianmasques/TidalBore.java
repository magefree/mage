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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ReturnToHandTargetPermanentCost;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author markedagain
 */
public class TidalBore extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an Island");

    static {
        filter.add(new SubtypePredicate("Island"));
        filter.add(new CardTypePredicate(CardType.LAND));
    }
    
    public TidalBore(UUID ownerId) {
        super(ownerId, 109, "Tidal Bore", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "MMQ";

        // You may return an Island you control to its owner's hand rather than pay Tidal Bore's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ReturnToHandTargetPermanentCost(new TargetControlledPermanent(filter))));
        // You may tap or untap target creature.
        this.getSpellAbility().addEffect(new MayTapOrUntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TidalBore(final TidalBore card) {
        super(card);
    }

    @Override
    public TidalBore copy() {
        return new TidalBore(this);
    }
}
