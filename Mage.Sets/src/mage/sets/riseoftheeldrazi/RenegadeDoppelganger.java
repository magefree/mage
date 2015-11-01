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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author North
 */
public class RenegadeDoppelganger extends CardImpl {

    public RenegadeDoppelganger(UUID ownerId) {
        super(ownerId, 84, "Renegade Doppelganger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Whenever another creature enters the battlefield under your control, you may have Renegade Doppelganger become a copy of that creature until end of turn.
        this.addAbility(new RenegadeDoppelgangerTriggeredAbility());
    }

    public RenegadeDoppelganger(final RenegadeDoppelganger card) {
        super(card);
    }

    @Override
    public RenegadeDoppelganger copy() {
        return new RenegadeDoppelganger(this);
    }
}

class RenegadeDoppelgangerTriggeredAbility extends TriggeredAbilityImpl {

    RenegadeDoppelgangerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RenegadeDoppelgangerEffect(), true);
    }

    RenegadeDoppelgangerTriggeredAbility(final RenegadeDoppelgangerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RenegadeDoppelgangerTriggeredAbility copy() {
        return new RenegadeDoppelgangerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getCardType().contains(CardType.CREATURE) && permanent.getControllerId().equals(this.getControllerId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another creature enters the battlefield under your control, you may have {this} become a copy of that creature until end of turn.";
    }
}

class RenegadeDoppelgangerEffect extends OneShotEffect {

    public RenegadeDoppelgangerEffect() {
        super(Outcome.Benefit);
        this.staticText = "have {this} become a copy of that creature until end of turn";
    }

    public RenegadeDoppelgangerEffect(final RenegadeDoppelgangerEffect effect) {
        super(effect);
    }

    @Override
    public RenegadeDoppelgangerEffect copy() {
        return new RenegadeDoppelgangerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature == null) {
            targetCreature = (Permanent) game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        }
        if (targetCreature == null || permanent == null) {
            return false;
        }

        game.copyPermanent(Duration.EndOfTurn, targetCreature, permanent.getId(), source, new EmptyApplyToPermanent());
        return false;
    }
}
