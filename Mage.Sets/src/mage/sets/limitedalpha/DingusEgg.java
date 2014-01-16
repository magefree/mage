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
package mage.sets.limitedalpha;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Quercitron
 */
public class DingusEgg extends CardImpl<DingusEgg> {

    public DingusEgg(UUID ownerId) {
        super(ownerId, 241, "Dingus Egg", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "LEA";

        // Whenever a land is put into a graveyard from the battlefield, Dingus Egg deals 2 damage to that land's controller.
        this.addAbility(new DingusEggTriggeredAbility());
    }

    public DingusEgg(final DingusEgg card) {
        super(card);
    }

    @Override
    public DingusEgg copy() {
        return new DingusEgg(this);
    }
}

class DingusEggTriggeredAbility extends TriggeredAbilityImpl<DingusEggTriggeredAbility> {

    public DingusEggTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
    }

    public DingusEggTriggeredAbility(final DingusEggTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD
                    && zEvent.getToZone() == Zone.GRAVEYARD
                    && zEvent.getTarget().getCardType().contains(CardType.LAND)) {
                
                if (getTargets().size() == 0) {
                    UUID targetId = zEvent.getTarget().getControllerId();
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(targetId));
                    }
                }
                return true;
                
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land is put into a graveyard from the battlefield, Dingus Egg deals 2 damage to that land's controller";
    }

    @Override
    public DingusEggTriggeredAbility copy() {
        return new DingusEggTriggeredAbility(this);
    }
}
