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
package mage.sets.magic2015;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class HotSoup extends CardImpl {

    public HotSoup(UUID ownerId) {
        super(ownerId, 219, "Hot Soup", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "M15";
        this.subtype.add("Equipment");

        // Equipped creature can't be blocked.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT)));
        
        // Whenever equipped creature is dealt damage, destroy it.
        this.addAbility(new HotSoupTriggeredAbility());
        
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    public HotSoup(final HotSoup card) {
        super(card);
    }

    @Override
    public HotSoup copy() {
        return new HotSoup(this);
    }
}

class HotSoupTriggeredAbility extends TriggeredAbilityImpl {
    
    HotSoupTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }
    
    HotSoupTriggeredAbility(final HotSoupTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public HotSoupTriggeredAbility copy() {
        return new HotSoupTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            if (event.getTargetId() == equipment.getAttachedTo()) {
                for(Effect effect : this.getEffects())
                {
                    effect.setTargetPointer(new FixedTarget(equipment.getAttachedTo()));
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever equipped creature is dealt damage, " + super.getRule();
    }
}