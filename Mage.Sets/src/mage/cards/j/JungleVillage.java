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
package mage.cards.j;

import mage.MageObject;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class JungleVillage extends CardImpl {

    public JungleVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Add {C} to your manapool.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Jungle Village: Search your library for a basic Mountain, Forest or Plains card and put it onto the battlefield tapped. Then shuffle your library.
        this.addAbility(new JungleVillageAbility());

    }

    public JungleVillage(final JungleVillage card) {
        super(card);
    }

    @Override
    public JungleVillage copy() {
        return new JungleVillage(this);
    }

    public class JungleVillageAbility extends ActivatedAbilityImpl {

        public JungleVillageAbility(JungleVillageAbility ability) {
            super(ability);
        }

        public JungleVillageAbility() {
            super(Zone.BATTLEFIELD, null);
            addCost(new TapSourceCost());
            addCost(new SacrificeSourceCost());
            FilterCard filter = new FilterCard("basic Mountain, Forest or Plains");
            filter.add(new CardTypePredicate(CardType.LAND));
            ArrayList<Predicate<MageObject>> subtypePredicates = new ArrayList<>();
            subtypePredicates.add(new SubtypePredicate("Plains"));
            subtypePredicates.add(new SubtypePredicate("Mountain"));
            subtypePredicates.add(new SubtypePredicate("Forest"));
            filter.add(Predicates.or(subtypePredicates));
            filter.add(new SupertypePredicate(SuperType.BASIC));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            addEffect(new SearchLibraryPutInPlayEffect(target, true, true, Outcome.PutLandInPlay));
        }

        @Override
        public JungleVillageAbility copy() {
            return new JungleVillageAbility(this);
        }
    }
}
