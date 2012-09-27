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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public class HandOfEmrakul extends CardImpl<HandOfEmrakul> {

    private static final String ALTERNATIVE_COST_DESCRIPTION = "You may sacrifice four Eldrazi Spawn rather than pay Hand of Emrakul's mana cost";
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Eldrazi Spawn");

    static {
        filter.add(new NamePredicate("Eldrazi Spawn"));
    }

    public HandOfEmrakul(UUID ownerId) {
        super(ownerId, 5, "Hand of Emrakul", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{9}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Eldrazi");

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // You may sacrifice four Eldrazi Spawn rather than pay Hand of Emrakul's mana cost.
        this.getSpellAbility().addAlternativeCost(new AlternativeCostImpl(ALTERNATIVE_COST_DESCRIPTION,
                new SacrificeTargetCost(new TargetControlledPermanent(4, 4, filter, true))));
        this.addAbility(new AnnihilatorAbility(1));
    }

    public HandOfEmrakul(final HandOfEmrakul card) {
        super(card);
    }

    @Override
    public HandOfEmrakul copy() {
        return new HandOfEmrakul(this);
    }
}
