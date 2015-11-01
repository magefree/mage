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
package mage.sets.guildpact;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackAbility;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public class BurningTreeShaman extends CardImpl {

    public BurningTreeShaman(UUID ownerId) {
        super(ownerId, 105, "Burning-Tree Shaman", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Centaur");
        this.subtype.add("Shaman");

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever a player activates an ability that isn't a mana ability, Burning-Tree Shaman deals 1 damage to that player.
        this.addAbility(new BurningTreeShamanTriggeredAbility());
    }

    public BurningTreeShaman(final BurningTreeShaman card) {
        super(card);
    }

    @Override
    public BurningTreeShaman copy() {
        return new BurningTreeShaman(this);
    }
}

class BurningTreeShamanTriggeredAbility extends TriggeredAbilityImpl {

    BurningTreeShamanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1, false, "that player"));
        this.addTarget(new TargetPlayer());
    }

    BurningTreeShamanTriggeredAbility(final BurningTreeShamanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BurningTreeShamanTriggeredAbility copy() {
        return new BurningTreeShamanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility != null && stackAbility.getAbilityType() == AbilityType.ACTIVATED) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player activates an ability that isn't a mana ability, {this} deals 1 damage to that player.";
    }
}
