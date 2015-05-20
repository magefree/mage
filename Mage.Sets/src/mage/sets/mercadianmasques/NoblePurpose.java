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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public class NoblePurpose extends CardImpl {

    public NoblePurpose(UUID ownerId) {
        super(ownerId, 32, "Noble Purpose", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");
        this.expansionSetCode = "MMQ";


        // Whenever a creature you control deals combat damage, you gain that much life.
        this.addAbility(new NoblePurposeTriggeredAbility());
    }

    public NoblePurpose(final NoblePurpose card) {
        super(card);
    }

    @Override
    public NoblePurpose copy() {
        return new NoblePurpose(this);
    }
}

class NoblePurposeTriggeredAbility extends TriggeredAbilityImpl {

    public NoblePurposeTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }
    
    public NoblePurposeTriggeredAbility(final NoblePurposeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NoblePurposeTriggeredAbility copy() {
        return new NoblePurposeTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedEvent) {
            DamagedEvent damageEvent = (DamagedEvent) event;
            if (damageEvent.isCombatDamage()) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)
                        && permanent.getControllerId().equals(this.getControllerId())) {
                    this.getEffects().clear();
                    this.getEffects().add(new GainLifeEffect(damageEvent.getAmount()));
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage, you gain that much life.";
    }
    
}
