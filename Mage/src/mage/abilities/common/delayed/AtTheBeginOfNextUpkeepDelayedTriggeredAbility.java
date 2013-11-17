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
package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */

public class AtTheBeginOfNextUpkeepDelayedTriggeredAbility extends DelayedTriggeredAbility<AtTheBeginOfNextUpkeepDelayedTriggeredAbility> {

    public AtTheBeginOfNextUpkeepDelayedTriggeredAbility(Effect effect) {
        this(effect, Duration.Custom);
    }

    public AtTheBeginOfNextUpkeepDelayedTriggeredAbility(Effect effect, Duration duration) {
        super(effect, duration);
    }

    public AtTheBeginOfNextUpkeepDelayedTriggeredAbility(AtTheBeginOfNextUpkeepDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheBeginOfNextUpkeepDelayedTriggeredAbility copy() {
        return new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        String text = modes.getText();
        if (!text.isEmpty()) {
            sb.append(Character.toUpperCase(text.charAt(0)));
            if (text.endsWith(".")) {
                sb.append(text.substring(1, text.length()-1));
            } else {
                sb.append(text.substring(1));
            }
        }
        return sb.append(" at the beginning of the next turn's upkeep.").toString();
    }
}
