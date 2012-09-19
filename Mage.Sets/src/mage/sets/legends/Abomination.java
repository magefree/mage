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
package mage.sets.legends;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class Abomination extends CardImpl<Abomination> {

    public Abomination(UUID ownerId) {
        super(ownerId, 1, "Abomination", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "LEG";
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Whenever Abomination blocks or becomes blocked by a green or white creature, destroy that creature at end of combat.
        this.addAbility(new AbominationTriggeredAbility());
    }

    public Abomination(final Abomination card) {
        super(card);
    }

    @Override
    public Abomination copy() {
        return new Abomination(this);
    }
}

class AbominationTriggeredAbility extends TriggeredAbilityImpl<AbominationTriggeredAbility> {

    AbominationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AbominationEffect());
    }

    AbominationTriggeredAbility(final AbominationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AbominationTriggeredAbility copy() {
        return new AbominationTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            Permanent blocked = game.getPermanent(event.getTargetId());
            Permanent abomination = game.getPermanent(sourceId);
            if (blocker != null && blocker != abomination
                    && blocker.getColor().isWhite()
                    && blocked == abomination) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                    return true;
                }
            }
            if (blocker != null && blocker == abomination
                    && game.getPermanent(event.getTargetId()).getColor().isWhite()) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    return true;
                }
            }
            if (blocker != null && blocker != abomination
                    && blocker.getColor().isGreen()
                    && blocked == abomination) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                    return true;
                }
            }
            if (blocker != null && blocker == abomination
                    && game.getPermanent(event.getTargetId()).getColor().isGreen()) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks or becomes blocked by a green or white creature, destroy that creature at end of combat.";
    }
}

class AbominationEffect extends OneShotEffect<AbominationEffect> {

    AbominationEffect() {
        super(Outcome.Detriment);
        staticText = "Destroy that creature at the end of combat";
    }

    AbominationEffect(final AbominationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability event) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, event));
        if (permanent != null) {
            AtTheEndOfCombatDelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect());
            delayedAbility.setSourceId(permanent.getId());
            delayedAbility.setControllerId(event.getControllerId());
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }

    @Override
    public AbominationEffect copy() {
        return new AbominationEffect(this);
    }
}
