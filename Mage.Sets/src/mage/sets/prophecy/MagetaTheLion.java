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
package mage.sets.prophecy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public class MagetaTheLion extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("creatures except for {this}");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new AnotherPredicate());
    }

    public MagetaTheLion(UUID ownerId) {
        super(ownerId, 13, "Mageta the Lion", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "PCY";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Spellshaper");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{W}{W}, {tap}, Discard two cards: Destroy all creatures except for Mageta the Lion. Those creatures can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filter, true), new ManaCostsImpl("{2}{W}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2,2, new FilterCard("two cards"))));
        this.addAbility(ability);
    }

    public MagetaTheLion(final MagetaTheLion card) {
        super(card);
    }

    @Override
    public MagetaTheLion copy() {
        return new MagetaTheLion(this);
    }
}
