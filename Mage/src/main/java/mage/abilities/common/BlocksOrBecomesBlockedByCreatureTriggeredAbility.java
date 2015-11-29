/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class BlocksOrBecomesBlockedByCreatureTriggeredAbility extends TriggeredAbilityImpl {

    // note that this is using the Filter#match(E e, Game game),
    // not FilterInPlay#(E o, UUID sourceId, UUID playerId, Game game)
    // this triggers on both blocked and blocking, so source and player don't have a consistent definition
    protected FilterPermanent filter;
    protected String rule;

    public BlocksOrBecomesBlockedByCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, new FilterCreaturePermanent(), optional, null);
    }

    public BlocksOrBecomesBlockedByCreatureTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        this(effect, filter, optional, null);
    }

    public BlocksOrBecomesBlockedByCreatureTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, String rule) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.rule = rule;
    }

    public BlocksOrBecomesBlockedByCreatureTriggeredAbility(final BlocksOrBecomesBlockedByCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Permanent blocked = game.getPermanent(event.getTargetId());
            if (blocked != null && filter.match(blocked, game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            if (blocker != null && filter.match(blocker, game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        if (rule != null) {
            return rule;
        }
        return "Whenever {this} blocks or becomes blocked by a " + filter.getMessage() + ", " + super.getRule();
    }

    @Override
    public BlocksOrBecomesBlockedByCreatureTriggeredAbility copy() {
        return new BlocksOrBecomesBlockedByCreatureTriggeredAbility(this);
    }
}
