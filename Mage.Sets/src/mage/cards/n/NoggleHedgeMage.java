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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 *
 */
public class NoggleHedgeMage extends CardImpl {

    private final static FilterLandPermanent filter = new FilterLandPermanent();
    private final static FilterLandPermanent filter2 = new FilterLandPermanent();

    static {
        filter.add(new SubtypePredicate("Island"));
        filter2.add(new SubtypePredicate("Mountain"));
    }

    private String rule = "When {this} enters the battlefield, if you control two or more Islands, you may tap two target permanents.";
    private String rule2 = "When {this} enters the battlefield, if you control two or more Mountains, you may have {this} deal 2 damage to target player.";

    public NoggleHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U/R}");
        this.subtype.add("Noggle");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Noggle Hedge-Mage enters the battlefield, if you control two or more Islands, you may tap two target permanents.
        Ability ability = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), true), new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1), rule);
        ability.addTarget(new TargetPermanent(2, new FilterPermanent()));
        this.addAbility(ability);

        // When Noggle Hedge-Mage enters the battlefield, if you control two or more Mountains, you may have Noggle Hedge-Mage deal 2 damage to target player.
        Ability ability2 = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2), true), new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 1), rule2);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);
    }

    public NoggleHedgeMage(final NoggleHedgeMage card) {
        super(card);
    }

    @Override
    public NoggleHedgeMage copy() {
        return new NoggleHedgeMage(this);
    }
}
