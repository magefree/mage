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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.EldraziSpawnToken;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class RapaciousOne extends CardImpl<RapaciousOne> {

    public RapaciousOne(UUID ownerId) {
        super(ownerId, 162, "Rapacious One", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Eldrazi");
        this.subtype.add("Drone");

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new RapaciousOneTriggeredAbility());
    }

    public RapaciousOne(final RapaciousOne card) {
        super(card);
    }

    @Override
    public RapaciousOne copy() {
        return new RapaciousOne(this);
    }
}

class RapaciousOneTriggeredAbility extends TriggeredAbilityImpl<RapaciousOneTriggeredAbility> {

    public RapaciousOneTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public RapaciousOneTriggeredAbility(final RapaciousOneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RapaciousOneTriggeredAbility copy() {
        return new RapaciousOneTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)
                && ((DamagedPlayerEvent) event).isCombatDamage()) {
            this.addEffect(new CreateTokenEffect(new EldraziSpawnToken(), event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, put that many 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add {1} to your mana pool.\"";
    }
}
