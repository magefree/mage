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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author Backfir3
 */
public class PiousWarrior extends CardImpl<PiousWarrior> {

    public PiousWarrior(UUID ownerId) {
        super(ownerId, 34, "Pious Warrior", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "MMQ";
        this.subtype.add("Human");
        this.subtype.add("Rebel");
        this.subtype.add("Warrior");
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

		// Whenever Pious Warrior is dealt combat damage, you gain that much life.
        this.addAbility(new PiousWarriorTriggeredAbility());
    }

    public PiousWarrior(final PiousWarrior card) {
        super(card);
    }

    @Override
    public PiousWarrior copy() {
        return new PiousWarrior(this);
    }
}

class PiousWarriorTriggeredAbility extends TriggeredAbilityImpl<PiousWarriorTriggeredAbility> {

    public PiousWarriorTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new PiousWarriorGainLifeEffect());
    }

    public PiousWarriorTriggeredAbility(final PiousWarriorTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public PiousWarriorTriggeredAbility copy() {
        return new PiousWarriorTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(this.sourceId)
                && ((DamagedCreatureEvent)event).isCombatDamage() ) {
   			this.getEffects().get(0).setValue("damageAmount", event.getAmount());
       		return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt combat damage, " + super.getRule();
    }
}


class PiousWarriorGainLifeEffect extends OneShotEffect<PiousWarriorGainLifeEffect> {

	public PiousWarriorGainLifeEffect() {
		super(Outcome.GainLife);
		staticText = "you gain that much life";
	}

    public PiousWarriorGainLifeEffect(final PiousWarriorGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public PiousWarriorGainLifeEffect copy() {
        return new PiousWarriorGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife((Integer) this.getValue("damageAmount"), game);
        }
        return true;
    }

}
