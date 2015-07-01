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
package mage.sets.magicorigins;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ThopterColorlessToken;

/**
 *
 * @author fireshoes
 */
public class ThopterSpyNetwork extends CardImpl {

    public ThopterSpyNetwork(UUID ownerId) {
        super(ownerId, 79, "Thopter Spy Network", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.expansionSetCode = "ORI";

        // At the beginning of your upkeep, if you control an artifact, put a 1/1 colorless Thopter artifact creature token with flying onto the battlefield.
        this.addAbility(new ThopterSpyNetworkUpkeepTriggeredAbility());
        
        // Whenever one or more artifact creatures you control deals combat damage to a player, draw a card.
        this.addAbility(new ThopterSpyNetworkDamageTriggeredAbility());
    }

    public ThopterSpyNetwork(final ThopterSpyNetwork card) {
        super(card);
    }

    @Override
    public ThopterSpyNetwork copy() {
        return new ThopterSpyNetwork(this);
    }
}

class ThopterSpyNetworkUpkeepTriggeredAbility extends TriggeredAbilityImpl {

    public ThopterSpyNetworkUpkeepTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ThopterColorlessToken("ORI"), 1), false);
    }

    public ThopterSpyNetworkUpkeepTriggeredAbility(final ThopterSpyNetworkUpkeepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThopterSpyNetworkUpkeepTriggeredAbility copy() {
        return new ThopterSpyNetworkUpkeepTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
    return game.getBattlefield().countAll(new FilterArtifactPermanent(), this.controllerId, game) >= 1;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control an artifact, put a 1/1 colorless Thopter artifact creature token with flying onto the battlefield";
    }
}

class ThopterSpyNetworkDamageTriggeredAbility extends TriggeredAbilityImpl {
    
    List<UUID> damagedPlayerIds = new ArrayList<>();

    public ThopterSpyNetworkDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public ThopterSpyNetworkDamageTriggeredAbility(final ThopterSpyNetworkDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThopterSpyNetworkDamageTriggeredAbility copy() {
        return new ThopterSpyNetworkDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
            || event.getType() == GameEvent.EventType.END_COMBAT_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                Permanent creature = game.getPermanent(event.getSourceId());
                if (creature != null && creature.getControllerId().equals(controllerId) 
                    && creature.getCardType().contains(CardType.ARTIFACT) && !damagedPlayerIds.contains(event.getTargetId())) {
                        damagedPlayerIds.add(event.getTargetId());
                  return true;
                }
            }
        }
        if (event.getType().equals(GameEvent.EventType.END_COMBAT_STEP_POST)){
            damagedPlayerIds.clear();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more artifact creatures you control deals combat damage to a player, draw a card";
    }
}