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

package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */

public class TurnedFaceUpAllTriggeredAbility extends TriggeredAbilityImpl {

    private FilterPermanent filter;
    private boolean setTargetPointer;

    public TurnedFaceUpAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, filter, false);
    }

    public TurnedFaceUpAllTriggeredAbility(Effect effect, FilterPermanent filter,  boolean setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, filter, setTargetPointer, false);
    }

    public TurnedFaceUpAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        // has to be set so the ability triggers if card itself is turn faced up
        this.setWorksFaceDown(true);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public TurnedFaceUpAllTriggeredAbility(final TurnedFaceUpAllTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
    }

    @Override
    public TurnedFaceUpAllTriggeredAbility copy() {
        return new TurnedFaceUpAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNEDFACEUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            MageObject sourceObj = this.getSourceObject(game);
            if (sourceObj != null) {
                if (sourceObj instanceof  Card && ((Card)sourceObj).isFaceDown(game)) {
                    // if face down and it's not itself that is turned face up, it does not trigger
                    return false;
                }
            } else {
                // Permanent is and was not on the battlefield
                return false;
            }
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (filter.match(permanent, getSourceId(), getControllerId(), game)) {
            if (setTargetPointer) {
                for (Effect effect: getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " is turned face up, " + super.getRule();
    }
}

