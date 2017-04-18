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
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.KithkinToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 *
 */
public class GwyllionHedgeMage extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Plains");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("Swamps");

    static {
        filter.add(new SubtypePredicate("Plains"));
        filter2.add(new SubtypePredicate("Swamp"));
    }

    private static final String rule1 = "When {this} enters the battlefield, if you control two or more Plains, you may create a 1/1 white Kithkin Soldier creature token.";
    private static final String rule2 = "When {this} enters the battlefield, if you control two or more Swamps, you may put a -1/-1 counter on target creature.";

    public GwyllionHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W/B}");
        this.subtype.add("Hag");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Gwyllion Hedge-Mage enters the battlefield, if you control two or more Plains, you may create a 1/1 white Kithkin Soldier creature token.
        Ability ability = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KithkinToken()), true), new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1), rule1);
        this.addAbility(ability);

        // When Gwyllion Hedge-Mage enters the battlefield, if you control two or more Swamps, you may put a -1/-1 counter on target creature.
        Ability ability2 = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()), true),
                new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 1),
                rule2);
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);

    }

    public GwyllionHedgeMage(final GwyllionHedgeMage card) {
        super(card);
    }

    @Override
    public GwyllionHedgeMage copy() {
        return new GwyllionHedgeMage(this);
    }
}
