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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class QuestForPureFlame extends CardImpl<QuestForPureFlame> {

    public QuestForPureFlame(UUID ownerId) {
        super(ownerId, 144, "Quest for Pure Flame", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        this.expansionSetCode = "ZEN";

        this.color.setRed(true);

        // Whenever a source you control deals damage to an opponent, you may put a quest counter on Quest for Pure Flame.
        this.addAbility(new QuestForPureFlameTriggeredAbility());

        // Remove four quest counters from Quest for Pure Flame and sacrifice it: If any source you control would deal damage to a creature or player this turn, it deals double that damage to that creature or player instead.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new QuestForPureFlameEffect(), new RemoveCountersSourceCost(CounterType.QUEST.createInstance(4)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public QuestForPureFlame(final QuestForPureFlame card) {
        super(card);
    }

    @Override
    public QuestForPureFlame copy() {
        return new QuestForPureFlame(this);
    }
}

class QuestForPureFlameTriggeredAbility extends TriggeredAbilityImpl<QuestForPureFlameTriggeredAbility> {

    public QuestForPureFlameTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true);
    }

    public QuestForPureFlameTriggeredAbility(final QuestForPureFlameTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuestForPureFlameTriggeredAbility copy() {
        return new QuestForPureFlameTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGED_PLAYER)
                && game.getOpponents(controllerId).contains(event.getTargetId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            Player player = game.getPlayer(event.getSourceId());
            StackObject spell = game.getStack().getStackObject(event.getSourceId());
            if (permanent != null && permanent.getControllerId().equals(controllerId)) {
                return true;
            }
            if (player != null && player.getId().equals(controllerId)) {
                return true;
            }
            if (spell != null && spell.getControllerId().equals(controllerId)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source you control deals damage to an opponent, you may put a quest counter on Quest for Pure Flame.";
    }
}

class QuestForPureFlameEffect extends ReplacementEffectImpl<QuestForPureFlameEffect> {

    public QuestForPureFlameEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Outcome.Damage);
        staticText = "If any source you control would deal damage to a creature or player this turn, it deals double that damage to that creature or player instead";
    }

    public QuestForPureFlameEffect(final QuestForPureFlameEffect effect) {
        super(effect);
    }

    @Override
    public QuestForPureFlameEffect copy() {
        return new QuestForPureFlameEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGE_CREATURE)) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            Player player = game.getPlayer(event.getSourceId());
            StackObject spell = game.getStack().getStackObject(event.getSourceId());

            if (permanent != null && permanent.getControllerId().equals(source.getControllerId())) {
                event.setAmount(event.getAmount() * 2);
            }
            if (player != null && player.getId().equals(source.getControllerId())) {
                event.setAmount(event.getAmount() * 2);
            }
            if (spell != null && spell.getControllerId().equals(source.getControllerId())) {
                event.setAmount(event.getAmount() * 2);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }
}