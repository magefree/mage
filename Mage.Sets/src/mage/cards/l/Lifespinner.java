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
package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class Lifespinner extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("legendary Spirit permanent card");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
        filter.add(new SubtypePredicate("Spirit"));
    }

    public Lifespinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add("Spirit");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {t}, Sacrifice three Spirits: Search your library for a legendary Spirit permanent card and put it onto the battlefield. Then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)),
                new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(3, 3, new FilterControlledCreaturePermanent("Spirit", "three Spirits"), false)));
        this.addAbility(ability);
    }

    public Lifespinner(final Lifespinner card) {
        super(card);
    }

    @Override
    public Lifespinner copy() {
        return new Lifespinner(this);
    }
}
