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
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public class TreefolkHarbinger extends CardImpl<TreefolkHarbinger> {

    private static final FilterCard filter = new FilterCard("a Treefolk or Forest card");
    static {
       filter.add(Predicates.or(new SubtypePredicate("Treefolk"), new SubtypePredicate("Forest")));
    }
    
    public TreefolkHarbinger(UUID ownerId) {
        super(ownerId, 239, "Treefolk Harbinger", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Treefolk");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Treefolk Harbinger enters the battlefield, you may search your library for a Treefolk or Forest card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true, true), true));
    }

    public TreefolkHarbinger(final TreefolkHarbinger card) {
        super(card);
    }

    @Override
    public TreefolkHarbinger copy() {
        return new TreefolkHarbinger(this);
    }
}
