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
package mage.sets.legions;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author cbt33
 */
public class ToxinSliver extends CardImpl {

    public ToxinSliver(UUID ownerId) {
        super(ownerId, 84, "Toxin Sliver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "LGN";
        this.subtype.add("Sliver");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals combat damage to a creature, destroy that creature. It can't be regenerated.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(new DestroyTargetEffect(true), true, false, true));

    }

    public ToxinSliver(final ToxinSliver card) {
        super(card);
    }

    @Override
    public ToxinSliver copy() {
        return new ToxinSliver(this);
    }
}

class DealsDamageTriggeredAbility extends TriggeredAbilityImpl {

    private boolean setTargetPointer;

    public DealsDamageTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
    }

    public DealsDamageTriggeredAbility(final DealsDamageTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsDamageTriggeredAbility copy() {
        return new DealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
            if (game.getPermanent(event.getSourceId()).hasSubtype("Sliver", game)) {
                if (setTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(game.getControllerId(event.getTargetId())));
                        effect.setValue("damage", event.getAmount());
                    }
                }
                return true;
            }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Sliver deals damage to a creature" + super.getRule();
    }

}
