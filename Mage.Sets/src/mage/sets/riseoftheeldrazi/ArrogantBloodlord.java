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
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.effects.OneShotEffect;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.cards.CardImpl;

/**
 *
 * @author jeffwadsworth
 */
public class ArrogantBloodlord extends CardImpl<ArrogantBloodlord> {

    public ArrogantBloodlord(UUID ownerId) {
        super(ownerId, 94, "Arrogant Bloodlord", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Vampire");
        this.subtype.add("Knight");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Arrogant Bloodlord blocks or becomes blocked by a creature with power 1 or less, destroy Arrogant Bloodlord at end of combat.
        this.addAbility(new ArrogantBloodlordTriggeredAbility());
    }

    public ArrogantBloodlord(final ArrogantBloodlord card) {
        super(card);
    }

    @Override
    public ArrogantBloodlord copy() {
        return new ArrogantBloodlord(this);
    }
}

class ArrogantBloodlordTriggeredAbility extends TriggeredAbilityImpl<ArrogantBloodlordTriggeredAbility> {

    ArrogantBloodlordTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ArrogantBloodlordEffect());
    }

    ArrogantBloodlordTriggeredAbility(final ArrogantBloodlordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArrogantBloodlordTriggeredAbility copy() {
        return new ArrogantBloodlordTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            Permanent arrogantBloodlord = game.getPermanent(sourceId);
            if (blocker != null && blocker != arrogantBloodlord && blocker.getPower().getValue() < 2) {
                return true;
            }
            if (blocker != null && blocker == arrogantBloodlord && game.getPermanent(event.getTargetId()).getPower().getValue() < 2) {
                    return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Arrogant Bloodlord blocks or becomes blocked by a creature with power 1 or less, destroy Arrogant Bloodlord at end of combat.";
    }
}

class ArrogantBloodlordEffect extends OneShotEffect<ArrogantBloodlordEffect> {
    
    ArrogantBloodlordEffect() {
        super(Outcome.Detriment);
        staticText = "Destroy Arrogant Bloodlord at the end of combat";
    }
    
    ArrogantBloodlordEffect(final ArrogantBloodlordEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            AtTheEndOfCombatDelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect());
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(source.getSourceId()));
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
    
    @Override
    public ArrogantBloodlordEffect copy() {
        return new ArrogantBloodlordEffect(this);
    }
}
    
    
