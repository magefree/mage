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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.HasCounterCondition;
import mage.abilities.condition.common.OpponentLostLifeCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class BloodchiefAscension extends CardImpl<BloodchiefAscension> {

    public BloodchiefAscension(UUID ownerId) {
        super(ownerId, 82, "Bloodchief Ascension", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.expansionSetCode = "ZEN";

        this.color.setBlack(true);

        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.QUEST.createInstance(), false),
                TargetController.ANY,
                new OpponentLostLifeCondition(Condition.ComparisonType.GreaterThan, 1),
                true));

        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.
        this.addAbility(new BloodchiefAscensionTriggeredAbility());

    }

    public BloodchiefAscension(final BloodchiefAscension card) {
        super(card);
    }

    @Override
    public BloodchiefAscension copy() {
        return new BloodchiefAscension(this);
    }
}

class BloodchiefAscensionTriggeredAbility extends TriggeredAbilityImpl<BloodchiefAscensionTriggeredAbility> {

    private Condition condition;

    public BloodchiefAscensionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2), true);
        this.addEffect(new GainLifeEffect(2));
        condition = new HasCounterCondition(CounterType.QUEST, 3);
    }

    public BloodchiefAscensionTriggeredAbility(final BloodchiefAscensionTriggeredAbility ability) {
        super(ability);
        this.condition = ability.condition;
    }

    @Override
    public BloodchiefAscensionTriggeredAbility copy() {
        return new BloodchiefAscensionTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return condition.apply(game, this);
    }


    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && game.getOpponents(controllerId).contains(card.getOwnerId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(card.getOwnerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a card is put into an opponent's graveyard from anywhere, if {this} has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.";
    }
}