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
package mage.cards.r;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public class RathiAssassin extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Mercenary permanent card with converted mana cost 3 or less");
    private static final FilterCreaturePermanent destroyFilter = new FilterCreaturePermanent("tapped nonblack creature");

    static {
        filter.add(new SubtypePredicate("Mercenary"));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
        destroyFilter.add(new TappedPredicate());
        destroyFilter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public RathiAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add("Zombie");
        this.subtype.add("Mercenary");
        this.subtype.add("Assassin");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{B}{B}, {T}: Destroy target tapped nonblack creature.
        Ability destroyAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{1}{B}{B}"));
        destroyAbility.addCost(new TapSourceCost());
        destroyAbility.addTarget(new TargetCreaturePermanent(destroyFilter));
        this.addAbility(destroyAbility);
        
        // {3}, {T}: Search your library for a Mercenary permanent card with converted mana cost 3 or less and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new TapSourceCost());
        ability.addManaCost(new GenericManaCost(3));
        this.addAbility(ability);
    }

    public RathiAssassin(final RathiAssassin card) {
        super(card);
    }

    @Override
    public RathiAssassin copy() {
        return new RathiAssassin(this);
    }

}
