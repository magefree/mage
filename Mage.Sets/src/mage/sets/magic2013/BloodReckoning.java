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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.effects.common.LoseLifeTargetEffect;

/**
 *
 * @author jeffwadsworth
 */
public class BloodReckoning extends CardImpl<BloodReckoning> {

    public BloodReckoning(UUID ownerId) {
        super(ownerId, 81, "Blood Reckoning", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "M13";

        this.color.setBlack(true);

        // Whenever a creature attacks you or a planeswalker you control, that creature's controller loses 1 life.
        this.addAbility(new BloodReckoningTriggeredAbility());
    }

    public BloodReckoning(final BloodReckoning card) {
        super(card);
    }

    @Override
    public BloodReckoning copy() {
        return new BloodReckoning(this);
    }
}

class BloodReckoningTriggeredAbility extends TriggeredAbilityImpl<BloodReckoningTriggeredAbility> {

    public BloodReckoningTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
    }

    public BloodReckoningTriggeredAbility(final BloodReckoningTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodReckoningTriggeredAbility copy() {
        return new BloodReckoningTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            Permanent attacker = game.getPermanent(event.getSourceId());
            if (attacker != null) {
                // check attacks you
                boolean youOrYourPlaneswalker = false;
                boolean you = event.getTargetId().equals(this.getControllerId());
                if (you) {
                    youOrYourPlaneswalker = true;
                } else{ // check attacks your planeswalker
                Permanent permanent = game.getPermanent(event.getTargetId());
                youOrYourPlaneswalker = permanent != null
                        && permanent.getCardType().contains(CardType.PLANESWALKER)
                        && permanent.getControllerId().equals(this.getControllerId());
                }
                if (youOrYourPlaneswalker) {
                    for (Effect effect : this.getEffects()) {
                        UUID controller = attacker.getControllerId();
                        if (controller != null) {
                            effect.setTargetPointer(new FixedTarget(controller));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks you or a planeswalker you control, that creature's controller loses 1 life.";
    }
}
