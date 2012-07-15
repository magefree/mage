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
package mage.sets.avacynrestored;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public class DemonlordOfAshmouth extends CardImpl<DemonlordOfAshmouth> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(" another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public DemonlordOfAshmouth(UUID ownerId) {
        super(ownerId, 96, "Demonlord of Ashmouth", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Demon");

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // When Demonlord of Ashmouth enters the battlefield, exile it unless you sacrifice another creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter)))));

        this.addAbility(new UndyingAbility());
    }

    public DemonlordOfAshmouth(final DemonlordOfAshmouth card) {
        super(card);
    }

    @Override
    public DemonlordOfAshmouth copy() {
        return new DemonlordOfAshmouth(this);
    }
}
