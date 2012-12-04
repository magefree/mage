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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class QuestForTheNihilStone extends CardImpl<QuestForTheNihilStone> {

    public QuestForTheNihilStone(UUID ownerId) {
        super(ownerId, 64, "Quest for the Nihil Stone", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.expansionSetCode = "WWK";

        this.color.setBlack(true);

        // Whenever an opponent discards a card, you may put a quest counter on Quest for the Nihil Stone.
        this.addAbility(new QuestForTheNihilStoneTriggeredAbility());

        // At the beginning of each opponent's upkeep, if that player has no cards in hand and Quest for the Nihil Stone has two or more quest counters on it, you may have that player lose 5 life.
        this.addAbility(new QuestForTheNihilStoneTriggeredAbility2());
    }

    public QuestForTheNihilStone(final QuestForTheNihilStone card) {
        super(card);
    }

    @Override
    public QuestForTheNihilStone copy() {
        return new QuestForTheNihilStone(this);
    }
}

class QuestForTheNihilStoneTriggeredAbility extends TriggeredAbilityImpl<QuestForTheNihilStoneTriggeredAbility> {

    public QuestForTheNihilStoneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance(), true), true);
    }

    public QuestForTheNihilStoneTriggeredAbility(final QuestForTheNihilStoneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuestForTheNihilStoneTriggeredAbility copy() {
        return new QuestForTheNihilStoneTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DISCARDED_CARD && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent discards a card, you may put a quest counter on {this}.";
    }
}

class QuestForTheNihilStoneTriggeredAbility2 extends TriggeredAbilityImpl<QuestForTheNihilStoneTriggeredAbility2> {

    public QuestForTheNihilStoneTriggeredAbility2() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(5), true);
    }

    public QuestForTheNihilStoneTriggeredAbility2(final QuestForTheNihilStoneTriggeredAbility2 ability) {
        super(ability);
    }

    @Override
    public QuestForTheNihilStoneTriggeredAbility2 copy() {
        return new QuestForTheNihilStoneTriggeredAbility2(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent quest = game.getPermanent(super.getSourceId());
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null
                    && opponent.getHand().size() == 0
                    && quest != null
                    && quest.getCounters().getCount(CounterType.QUEST) >= 2) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(opponent.getId()));
                    return true;
                }
            }
        }
        return false;
    }

        @Override
        public String getRule() {
        return "At the beginning of each opponent's upkeep, if that player has no cards in hand and {this} has two or more quest counters on it, you may have that player lose 5 life.";
        }
    }
