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

import mage.Constants.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyApplyToPermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public class RenegadeDoppelganger extends CardImpl<RenegadeDoppelganger> {

    public RenegadeDoppelganger(UUID ownerId) {
        super(ownerId, 84, "Renegade Doppelganger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Shapeshifter");

        this.color.setBlue(true);
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

class RenegadeDoppelgangerTriggeredAbility extends TriggeredAbilityImpl<RenegadeDoppelgangerTriggeredAbility> {

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
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && !event.getTargetId().equals(this.getSourceId())) {
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

class RenegadeDoppelgangerEffect extends ContinuousEffectImpl<RenegadeDoppelgangerEffect> {

    RenegadeDoppelgangerEffect() {
        super(Duration.EndOfTurn, Layer.CopyEffects_1, SubLayer.NA, Outcome.Copy);
    }

    RenegadeDoppelgangerEffect(final RenegadeDoppelgangerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (creature == null || permanent == null) {
            return false;
        }

        game.copyPermanent(creature, permanent, source, new EmptyApplyToPermanent());

        return true;
    }

    @Override
    public RenegadeDoppelgangerEffect copy() {
        return new RenegadeDoppelgangerEffect(this);
    }
}
