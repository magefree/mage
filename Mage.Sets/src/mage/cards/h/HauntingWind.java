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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class HauntingWind extends CardImpl {

    public HauntingWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever an artifact becomes tapped or a player activates an artifact's ability without {tap} in its activation cost, Haunting Wind deals 1 damage to that artifact's controller.
        this.addAbility(new HauntingWindTriggeredAbility());
    }

    public HauntingWind(final HauntingWind card) {
        super(card);
    }

    @Override
    public HauntingWind copy() {
        return new HauntingWind(this);
    }
}

class HauntingWindTriggeredAbility extends TriggeredAbilityImpl {

    HauntingWindTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    HauntingWindTriggeredAbility(final HauntingWindTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HauntingWindTriggeredAbility copy() {
        return new HauntingWindTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY
                || event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent == null || !permanent.isArtifact()) {
                return false;
            }
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (stackAbility == null) {
                return false;
            }
            String abilityText = stackAbility.getRule(true);
            if (abilityText.contains("{T}:") || abilityText.contains("{T},") || abilityText.contains("{T} or")) {
                return false;
            }
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent.getControllerId(), game));
            }
            return true;
        }
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null || !permanent.isArtifact()) {
                return false;
            }
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent.getControllerId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an artifact becomes tapped or a player activates an artifact's ability without {T} in its activation cost, {this} deals 1 damage to that artifact's controller.";
    }
}
