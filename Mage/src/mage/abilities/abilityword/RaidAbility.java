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

package mage.abilities.abilityword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author LevelX2
 */
public class RaidAbility extends TriggeredAbilityImpl {

    public RaidAbility(Card card, Effect effect) {
        this(card, effect, false);
    }

    public RaidAbility(Card card, Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        card.addWatcher(new PlayerAttackedWatcher());
        // this.setAbilityWord(AbilityWord.RAID); // not supported yet for rule generation
    }

    public RaidAbility(final RaidAbility ability) {
        super(ability);
    }

    @Override
    public RaidAbility copy() {
        return new RaidAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        // seems unneccessary to check twice because condition can't change until effect resolves, but who knows what the future brings
        PlayerAttackedWatcher watcher = (PlayerAttackedWatcher) game.getState().getWatchers().get("PlayerAttackedWatcher");
        return watcher != null && watcher.getNumberOfAttackersCurrentTurn(getControllerId()) > 0;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return EventType.ENTERS_THE_BATTLEFIELD.equals(event.getType()) && getSourceId().equals(event.getTargetId());
    }

    @Override
    public String getRule() {
        return "<i>Raid</i> - When {this} enters the battlefield, if you attacked with a creature this turn, " + super.getRule();
    }
}
