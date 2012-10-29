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
package mage.sets.ninthedition;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class Deathgazer extends CardImpl<Deathgazer> {

    public Deathgazer(UUID ownerId) {
        super(ownerId, 124, "Deathgazer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "9ED";
        this.subtype.add("Lizard");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Deathgazer blocks or becomes blocked by a nonblack creature, destroy that creature at end of combat.
        this.addAbility(new DeathgazerTriggeredAbility(new DestroyTargetEffect(), false));
    }

    public Deathgazer(final Deathgazer card) {
        super(card);
    }

    @Override
    public Deathgazer copy() {
        return new Deathgazer(this);
    }
}

class DeathgazerTriggeredAbility extends TriggeredAbilityImpl<DeathgazerTriggeredAbility> {

    
    
    public DeathgazerTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
    }

    public DeathgazerTriggeredAbility(final DeathgazerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            if (event.getSourceId().equals(this.getSourceId())) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if(permanent != null && !permanent.getColor().isBlack())
                {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    }
                    return true;
                }
            }
            if (event.getTargetId().equals(this.getSourceId())) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if(permanent != null && !permanent.getColor().isBlack())
                {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks or becomes blocked by a nonblack creature, " + super.getRule();
    }

    @Override
    public DeathgazerTriggeredAbility copy() {
        return new DeathgazerTriggeredAbility(this);
    }
}