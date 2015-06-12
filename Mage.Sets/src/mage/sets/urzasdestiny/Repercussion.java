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
package mage.sets.urzasdestiny;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
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
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author cbrianhill
 */
public class Repercussion extends CardImpl {

    public Repercussion(UUID ownerId) {
        super(ownerId, 95, "Repercussion", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        this.expansionSetCode = "UDS";

        // Whenever a creature is dealt damage, Repercussion deals that much damage to that creature's controller.
        this.addAbility(new RepercussionTriggeredAbility(new RepercussionEffect()));
    }

    public Repercussion(final Repercussion card) {
        super(card);
    }

    @Override
    public Repercussion copy() {
        return new Repercussion(this);
    }
}

class RepercussionTriggeredAbility extends TriggeredAbilityImpl {
    
    static final String PLAYER_DAMAGE_AMOUNT_KEY = "playerDamage";
    static final String TRIGGERING_CREATURE_KEY = "triggeringCreature";
    
    public RepercussionTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }
    
    public RepercussionTriggeredAbility(final RepercussionTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for(Effect effect : getEffects()) {
            effect.setValue(PLAYER_DAMAGE_AMOUNT_KEY, event.getAmount());
            effect.setValue(TRIGGERING_CREATURE_KEY, new MageObjectReference(event.getTargetId(), game));
        }
        return true;
    }
    
    @Override
    public String getRule() {
        return "Whenever a creature is dealt damage, {this} deals that much damage to that creature's controller.";
    }

    @Override
    public TriggeredAbility copy() {
        return new RepercussionTriggeredAbility(this);
    }
}

class RepercussionEffect extends OneShotEffect {
    
    public RepercussionEffect() {
        super(Outcome.Damage);
    }
    
    public RepercussionEffect(final RepercussionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer playerDamage = (Integer)this.getValue(RepercussionTriggeredAbility.PLAYER_DAMAGE_AMOUNT_KEY);
        MageObjectReference mor = (MageObjectReference)this.getValue(RepercussionTriggeredAbility.TRIGGERING_CREATURE_KEY);        
        if (playerDamage != null && mor != null) {
            Permanent creature = mor.getPermanentOrLKIBattlefield(game);
            if (creature != null) {
                Player player = game.getPlayer(creature.getControllerId());
                if (player != null) {
                    player.damage(playerDamage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new RepercussionEffect(this);
    }
    
}
