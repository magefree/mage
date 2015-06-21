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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox

 */
public class MerrowHarbinger extends CardImpl {

    public static final FilterCard filter = new FilterCard("Merfolk card");

    static {
        filter.add(new SubtypePredicate("Merfolk"));
    }

    public MerrowHarbinger(UUID ownerId) {
        super(ownerId, 73, "Merrow Harbinger", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // When Merrow Harbinger enters the battlefield, you may search your library for a Merfolk card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true, true), true));
    }

    public MerrowHarbinger(final MerrowHarbinger card) {
        super(card);
    }

    @Override
    public MerrowHarbinger copy() {
        return new MerrowHarbinger(this);
    }
}
