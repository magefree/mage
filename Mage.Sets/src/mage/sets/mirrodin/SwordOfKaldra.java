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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
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
 * @author LevelX2
 */
public class SwordOfKaldra extends CardImpl<SwordOfKaldra> {

    public SwordOfKaldra(UUID ownerId) {
        super(ownerId, 251, "Sword of Kaldra", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "MRD";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");

        // Equipped creature gets +5/+5.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(5,5, Duration.WhileOnBattlefield)));
        // Whenever equipped creature deals damage to a creature, exile that creature.
        this.addAbility(new SwordOfKaldraTriggeredAbility());
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl("{4}")));
    }

    public SwordOfKaldra(final SwordOfKaldra card) {
        super(card);
    }

    @Override
    public SwordOfKaldra copy() {
        return new SwordOfKaldra(this);
    }
}

class SwordOfKaldraTriggeredAbility extends TriggeredAbilityImpl<SwordOfKaldraTriggeredAbility> {

    public SwordOfKaldraTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect("exile that creature"), false);
    }

    public SwordOfKaldraTriggeredAbility(final SwordOfKaldraTriggeredAbility ability) {
            super(ability);
    }

    @Override
    public SwordOfKaldraTriggeredAbility copy() {
            return new SwordOfKaldraTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_CREATURE) {
            Permanent equipment = game.getPermanent(this.getSourceId());
            if (equipment != null
                && equipment.getAttachedTo() != null
                && event.getSourceId().equals(equipment.getAttachedTo())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }

        }
        return false;
    }

    @Override
    public String getRule() {
            return "Whenever equipped creature deals damage to a creature, " + super.getRule();
    }

}
