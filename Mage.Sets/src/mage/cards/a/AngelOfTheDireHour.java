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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class AngelOfTheDireHour extends CardImpl {

    public AngelOfTheDireHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add("Angel");

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Angel of the Dire Hour enters the battlefield, if you cast it from your hand, exile all attacking creatures.
        this.addAbility(new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ExileAllEffect(new FilterAttackingCreature("attacking creatures")), false),
                new CastFromHandSourceCondition(),
                "When {this} enters the battlefield, if you cast it from your hand, exile all attacking creatures."),
                new CastFromHandWatcher());
    }

    public AngelOfTheDireHour(final AngelOfTheDireHour card) {
        super(card);
    }

    @Override
    public AngelOfTheDireHour copy() {
        return new AngelOfTheDireHour(this);
    }
}
