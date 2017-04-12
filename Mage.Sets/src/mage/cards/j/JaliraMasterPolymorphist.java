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

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class JaliraMasterPolymorphist extends CardImpl {

    private static final FilterCreatureCard filterCard = new FilterCreatureCard("nonlegendary creature card");
    private static final FilterControlledCreaturePermanent filterPermanent = new FilterControlledCreaturePermanent("another creature");

    static {
        filterPermanent.add(new AnotherPredicate());
        filterCard.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
    }

    public JaliraMasterPolymorphist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{U}, {T}, Sacrifice another creature: Reveal cards from the top of your library until you reveal a nonlegendary creature card.
        // Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RevealCardsFromLibraryUntilEffect(filterCard, Zone.BATTLEFIELD, Zone.LIBRARY), new ManaCostsImpl("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterPermanent, true)));
        this.addAbility(ability);

    }

    public JaliraMasterPolymorphist(final JaliraMasterPolymorphist card) {
        super(card);
    }

    @Override
    public JaliraMasterPolymorphist copy() {
        return new JaliraMasterPolymorphist(this);
    }
}
