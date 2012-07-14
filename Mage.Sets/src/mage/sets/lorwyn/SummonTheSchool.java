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
package mage.sets.lorwyn;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.MerfolkToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public class SummonTheSchool extends CardImpl<SummonTheSchool> {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("untapped Merfolk you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate("Merfolk"));
    }

    public SummonTheSchool(UUID ownerId) {
        super(ownerId, 42, "Summon the School", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{W}");
        this.expansionSetCode = "LRW";
        this.supertype.add("Tribal");
        this.subtype.add("Merfolk");
        this.color.setWhite(true);
        // Put two 1/1 blue Merfolk Wizard creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MerfolkToken(), 2));
        // Tap four untapped Merfolk you control: Return Summon the School from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.GRAVEYARD, new ReturnToHandSourceEffect(), new TapTargetCost(new TargetControlledPermanent(4, 4, filter, false))));
    }

    public SummonTheSchool(final SummonTheSchool card) {
        super(card);
    }

    @Override
    public SummonTheSchool copy() {
        return new SummonTheSchool(this);
    }
}
