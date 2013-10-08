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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class BattleStrain extends CardImpl<BattleStrain> {

    public BattleStrain(UUID ownerId) {
        super(ownerId, 177, "Battle Strain", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.expansionSetCode = "ODY";

        this.color.setRed(true);

        // Whenever a creature blocks, Battle Strain deals 1 damage to that creature's controller.
        this.addAbility(new BattleStrainTriggeredAbility());
    }

    public BattleStrain(final BattleStrain card) {
        super(card);
    }

    @Override
    public BattleStrain copy() {
        return new BattleStrain(this);
    }
}

class BattleStrainTriggeredAbility extends TriggeredAbilityImpl<BattleStrainTriggeredAbility> {

    public BattleStrainTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    public BattleStrainTriggeredAbility(final BattleStrainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BattleStrainTriggeredAbility copy() {
        return new BattleStrainTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            if (blocker != null) {
                getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature blocks, {this} deals 1 damage to that creature's controller.";
    }
}
