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
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreatureOrPlayer;

import java.util.UUID;

/**
 * @author nantuko
 */
public class BurningVengeance extends CardImpl<BurningVengeance> {

    public BurningVengeance(UUID ownerId) {
        super(ownerId, 133, "Burning Vengeance", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "ISD";

        this.color.setRed(true);

        // Whenever you cast a spell from your graveyard, Burning Vengeance deals 2 damage to target creature or player.
        this.addAbility(new BurningVengeanceOnCastAbility());
    }

    public BurningVengeance(final BurningVengeance card) {
        super(card);
    }

    @Override
    public BurningVengeance copy() {
        return new BurningVengeance(this);
    }
}

class BurningVengeanceOnCastAbility extends TriggeredAbilityImpl<BurningVengeanceOnCastAbility> {

    private static final String abilityText = "Whenever you cast a spell from your graveyard, Burning Vengeance deals 2 damage to target creature or player";

    BurningVengeanceOnCastAbility() {
        super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
        TargetCreatureOrPlayer target = new TargetCreatureOrPlayer();
        target.setRequired(true);
        this.addTarget(target);
    }

    BurningVengeanceOnCastAbility(BurningVengeanceOnCastAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.getPlayerId().equals(controllerId)
                && event.getZone().equals(Constants.Zone.GRAVEYARD)) {
            return true;
        }
        return false;
    }

    @Override
    public BurningVengeanceOnCastAbility copy() {
        return new BurningVengeanceOnCastAbility(this);
    }

    @Override
    public String getRule() {
        return abilityText;
    }
}
