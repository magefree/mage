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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackObject;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class PsychicPurge extends CardImpl {

    public PsychicPurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Psychic Purge deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // When a spell or ability an opponent controls causes you to discard Psychic Purge, that player loses 5 life.
        Ability ability = new PsychicPurgeTriggeredAbility();
        this.addAbility(ability);
    }

    public PsychicPurge(final PsychicPurge card) {
        super(card);
    }

    @Override
    public PsychicPurge copy() {
        return new PsychicPurge(this);
    }
}

class PsychicPurgeTriggeredAbility extends TriggeredAbilityImpl {

    public PsychicPurgeTriggeredAbility() {
        super(Zone.GRAVEYARD, new LoseLifeTargetEffect(5), false);
    }

    public PsychicPurgeTriggeredAbility(final PsychicPurgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PsychicPurgeTriggeredAbility copy() {
        return new PsychicPurgeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getSourceId().equals(event.getTargetId())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null) {
                if (game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId())) {
                    Effect effect = this.getEffects().get(0);
                    effect.setTargetPointer(new FixedTarget(stackObject.getControllerId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a spell or ability an opponent controls causes you to discard {this}, that player loses 5 life.";
    }
}
