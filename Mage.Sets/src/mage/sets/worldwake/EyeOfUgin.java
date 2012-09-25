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

package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class EyeOfUgin extends CardImpl<EyeOfUgin> {

    private static final FilterCreatureCard filter = new FilterCreatureCard();
    private static final FilterCard filterSpells = new FilterCard("Colorless Eldrazi spells");

    static {
        filter.add(new ColorlessPredicate());
        filterSpells.add(new ColorlessPredicate());
        filterSpells.add(new SubtypePredicate("Eldrazi"));
    }

    public EyeOfUgin (UUID ownerId) {
        super(ownerId, 136, "Eye of Ugin", Rarity.MYTHIC, new CardType[]{CardType.LAND}, null);
        this.expansionSetCode = "WWK";
        this.supertype.add("Legendary");

        // Colorless Eldrazi spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionEffect(filterSpells, 2)));
        // {7}, {tap}: Search your library for a colorless creature card, reveal it, and put it into your hand. Then shuffle your library.
        Ability searchAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true),
                new TapSourceCost());
        searchAbility.addCost(new ManaCostsImpl("{7}"));
        this.addAbility(searchAbility);
    }

    public EyeOfUgin (final EyeOfUgin card) {
        super(card);
    }

    @Override
    public EyeOfUgin copy() {
        return new EyeOfUgin(this);
    }
}
