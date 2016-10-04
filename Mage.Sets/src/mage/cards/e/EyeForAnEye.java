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
package mage.sets.arabiannights;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author MarcoMarin
 */
public class EyeForAnEye extends CardImpl {

    public EyeForAnEye(UUID ownerId) {
        super(ownerId, 59, "Eye for an Eye", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}{W}");
        this.expansionSetCode = "ARN";

        // The next time a source of your choice would deal damage to you this turn, instead that source deals that much damage to you and Eye for an Eye deals that much damage to that source's controller.
        this.addAbility(new EyeForAnEyeTriggeredAbility(new EyeForAnEyeEffect()));
        
    }

    public EyeForAnEye(final EyeForAnEye card) {
        super(card);
    }

    @Override
    public EyeForAnEye copy() {
        return new EyeForAnEye(this);
    }
}

class EyeForAnEyeTriggeredAbility extends TriggeredAbilityImpl {

    public EyeForAnEyeTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public EyeForAnEyeTriggeredAbility(final EyeForAnEyeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EyeForAnEyeTriggeredAbility copy() {
        return new EyeForAnEyeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(event.getSourceId());
        this.getEffects().get(0).setValue("damageAmount", event.getAmount());
        this.getEffects().get(0).setTargetPointer(new FixedTarget(game.getControllerId(sourceObject.getId())));
        return true;
    }

    @Override
    public String getRule() {
        return "The next time a source of your choice would deal damage to you this turn, instead that source deals that much damage to you and {this} deals that much damage to that source's controller.";
    }
}

class EyeForAnEyeEffect extends OneShotEffect {

    public EyeForAnEyeEffect() {
        super(Outcome.Damage);
    }

    public EyeForAnEyeEffect(final EyeForAnEyeEffect effect) {
        super(effect);
    }

    @Override
    public EyeForAnEyeEffect copy() {
        return new EyeForAnEyeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damageAmount = (Integer) this.getValue("damageAmount");
        UUID targetId = this.targetPointer.getFirst(game, source);
        if (damageAmount != null && targetId != null) {
            Player player = game.getPlayer(targetId);
            if (player != null) {
                    player.damage(damageAmount, targetId, game, false, true);
                    return true;
            }
        }
        return false;
    }
}
