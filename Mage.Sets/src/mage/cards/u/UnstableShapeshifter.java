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
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyApplyToPermanent;
        

/**
 * 
 * @author HCrescent
 */
public class UnstableShapeshifter extends CardImpl {

    public UnstableShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Whenever another creature enters the battlefield under your control, you may have Renegade Doppelganger become a copy of that creature until end of turn.
        this.addAbility(new UnstableShapeshifterTriggeredAbility());
    }

    public UnstableShapeshifter(final UnstableShapeshifter card) {
        super(card);
    }

    @Override
    public UnstableShapeshifter copy() {
        return new UnstableShapeshifter(this);
    }
}

class UnstableShapeshifterTriggeredAbility extends TriggeredAbilityImpl {

    UnstableShapeshifterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UnstableShapeshifterEffect(), false);
    }

    UnstableShapeshifterTriggeredAbility(final UnstableShapeshifterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnstableShapeshifterTriggeredAbility copy() {
        return new UnstableShapeshifterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
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
        return "Whenever another creature enters the battlefield, {this} becomes a copy of that creature and gains this ability.";
    }
}

class UnstableShapeshifterEffect extends OneShotEffect {

    public UnstableShapeshifterEffect() {
        super(Outcome.Copy);
        this.staticText = "have {this} become a copy of that creature and gain this ability";
    }

    public UnstableShapeshifterEffect(final UnstableShapeshifterEffect effect) {
        super(effect);
    }

    @Override
    public UnstableShapeshifterEffect copy() {
        return new UnstableShapeshifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature == null) {
            targetCreature = (Permanent) game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        }
        Permanent newPermanent = game.copyPermanent( targetCreature, permanent.getId(), source, new EmptyApplyToPermanent());
        newPermanent.addAbility(new UnstableShapeshifterTriggeredAbility() , source.getSourceId(), game);
        return false;
    }
}
