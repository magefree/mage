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

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PlayTargetWithoutPayingManaEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import mage.abilities.effects.common.PutOntoBattlefieldTargetEffect;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StoneforgeMystic extends CardImpl<StoneforgeMystic> {

    private static final FilterCard filter = new FilterCard("an Equipment card");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new SubtypePredicate("Equipment"));
    }

    public StoneforgeMystic(UUID ownerId) {
        super(ownerId, 20, "Stoneforge Mystic", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "WWK";
        this.color.setWhite(true);
        this.subtype.add("Kor");
        this.subtype.add("Artificer");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Stoneforge Mystic enters the battlefield, you may search your library for an Equipment card, reveal it, put it into your hand, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true, true), true));

        // {1}{W}, {T}: You may put an Equipment card from your hand onto the battlefield.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOntoBattlefieldTargetEffect(false, true), new ManaCostsImpl("{1}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInHand(0, 1, filter));
        this.addAbility(ability);
    }

    public StoneforgeMystic(final StoneforgeMystic card) {
        super(card);
    }

    @Override
    public StoneforgeMystic copy() {
        return new StoneforgeMystic(this);
    }

}
