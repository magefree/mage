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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public class CarrionThrash extends CardImpl<CarrionThrash> {

    private static final FilterCreatureCard filter = new FilterCreatureCard("another creature card from your graveyard");

    static {
        filter.add(new AnotherPredicate());
    }

    public CarrionThrash(UUID ownerId) {
        super(ownerId, 162, "Carrion Thrash", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Viashino");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Carrion Thrash dies, you may pay {2}. If you do, return another target creature card from your graveyard to your hand.
        DiesTriggeredAbility ability = new DiesTriggeredAbility(new DoIfCostPaid(new ReturnToHandTargetEffect(), new GenericManaCost(2)), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public CarrionThrash(final CarrionThrash card) {
        super(card);
    }

    @Override
    public CarrionThrash copy() {
        return new CarrionThrash(this);
    }
}
