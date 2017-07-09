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
package mage.cards.s;

import java.util.UUID;
import mage.constants.CardType;
import mage.MageInt;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class SqueakingPieSneak extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Goblin card from your hand");
    static {
        filter.add(new SubtypePredicate(SubType.GOBLIN));
    }

    public SqueakingPieSneak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add("Goblin");
        this.subtype.add("Rogue");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As an additional cost to cast Squeaking Pie Sneak, reveal a Goblin card from your hand or pay {3}.
        this.getSpellAbility().addCost(new OrCost(
                new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(3),
                "reveal a Goblin card from your hand or pay {3}"));
        // Fear
        this.addAbility(FearAbility.getInstance());
    }

    public SqueakingPieSneak(final SqueakingPieSneak card) {
        super(card);
    }

    @Override
    public SqueakingPieSneak copy() {
        return new SqueakingPieSneak(this);
    }
}
