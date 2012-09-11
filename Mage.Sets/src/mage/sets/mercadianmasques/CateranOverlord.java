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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public class CateranOverlord extends CardImpl<CateranOverlord> {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Mercenary permanent card with converted mana cost 6 or less");

    static {
        filter.add(new SubtypePredicate("Mercenary"));
		filter.add(new ConvertedManaCostPredicate(ComparisonType.LessThan, 7));
    }

    public CateranOverlord(UUID ownerId) {
		super(ownerId, 123, "Cateran Overlord", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
		this.expansionSetCode = "MMQ";
		this.subtype.add("Horror");
		this.subtype.add("Mercenary");
		this.color.setBlack(true);
		this.power = new MageInt(7);
		this.toughness = new MageInt(5);
	
		// Sacrifice a creature: Regenerate Cateran Overlord.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RegenerateSourceEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent())));
		// {6}, {T}: Search your library for a Mercenary permanent card with converted mana cost 6 or less and put it onto the battlefield. Then shuffle your library.
		Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new TapSourceCost());
		ability.addManaCost(new GenericManaCost(6));
		this.addAbility(ability);
    }

    public CateranOverlord(final CateranOverlord card) {
        super(card);
    }

    @Override
    public CateranOverlord copy() {
        return new CateranOverlord(this);
    }

}