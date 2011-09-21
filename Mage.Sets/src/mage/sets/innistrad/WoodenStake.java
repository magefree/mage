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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author nantuko
 */
public class WoodenStake extends CardImpl<WoodenStake> {

    public WoodenStake(UUID ownerId) {
        super(ownerId, 237, "Wooden Stake", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Equipment");

        // Equip {1}
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(1)));

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));

        // Whenever equipped creature blocks or becomes blocked by a Vampire, destroy that creature. It can't be regenerated.
        this.addAbility(new WoodenStakeBlocksOrBecomesBlockedTriggeredAbility());
    }

    public WoodenStake(final WoodenStake card) {
        super(card);
    }

    @Override
    public WoodenStake copy() {
        return new WoodenStake(this);
    }
}

class WoodenStakeBlocksOrBecomesBlockedTriggeredAbility extends TriggeredAbilityImpl<WoodenStakeBlocksOrBecomesBlockedTriggeredAbility> {

    public WoodenStakeBlocksOrBecomesBlockedTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DestroyTargetEffect(true), false);
    }

    public WoodenStakeBlocksOrBecomesBlockedTriggeredAbility(final WoodenStakeBlocksOrBecomesBlockedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Permanent equipment = game.getPermanent(sourceId);
            if (equipment != null && equipment.getAttachedTo() != null) {
                if (event.getSourceId().equals(equipment.getAttachedTo())) {
                    Permanent blocks = game.getPermanent(event.getTargetId());
                    if (blocks != null && blocks.hasSubtype("Vampire")) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                        }
                        return true;
                    }
                    return false;
                }
                if (event.getTargetId().equals(equipment.getAttachedTo())) {
                    Permanent blockedBy = game.getPermanent(event.getSourceId());
                    if (blockedBy != null && blockedBy.hasSubtype("Vampire")) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                        }
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature blocks or becomes blocked by a Vampire, destroy that creature. It can't be regenerated.";
    }

    @Override
    public WoodenStakeBlocksOrBecomesBlockedTriggeredAbility copy() {
        return new WoodenStakeBlocksOrBecomesBlockedTriggeredAbility(this);
    }
}
