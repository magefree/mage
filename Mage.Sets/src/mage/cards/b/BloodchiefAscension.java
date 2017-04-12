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
package mage.cards.b;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.condition.common.OpponentLostLifeCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class BloodchiefAscension extends CardImpl {

    public BloodchiefAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");

        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.QUEST.createInstance(1), false),
                TargetController.ANY,
                new OpponentLostLifeCondition(ComparisonType.MORE_THAN, 1),
                true));

        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.
        Ability ability = new ConditionalTriggeredAbility(
                new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                        new LoseLifeTargetEffect(2), true, new FilterCard("a card"), TargetController.OPPONENT, SetTargetPointer.PLAYER),
                new SourceHasCounterCondition(CounterType.QUEST, 3),
                "Whenever a card is put into an opponent's graveyard from anywhere, if {this} has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life");
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);

    }

    public BloodchiefAscension(final BloodchiefAscension card) {
        super(card);
    }

    @Override
    public BloodchiefAscension copy() {
        return new BloodchiefAscension(this);
    }
}
