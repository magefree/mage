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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX
 */
public class KamiOfTheHonoredDead extends CardImpl<KamiOfTheHonoredDead> {

    public KamiOfTheHonoredDead(UUID ownerId) {
        super(ownerId, 12, "Kami of the Honored Dead", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying  
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Kami of the Honored Dead is dealt damage, you gain that much life.
        this.addAbility(new KamiOfTheHonoredDeadTriggeredAbility());
        // Soulshift 6 (When this creature dies, you may return target Spirit card with converted mana cost 6 or less from your graveyard to your hand.)
        this.addAbility(new SoulshiftAbility(6));
    }

    public KamiOfTheHonoredDead(final KamiOfTheHonoredDead card) {
        super(card);
    }

    @Override
    public KamiOfTheHonoredDead copy() {
        return new KamiOfTheHonoredDead(this);
    }
}

class KamiOfTheHonoredDeadTriggeredAbility extends TriggeredAbilityImpl<KamiOfTheHonoredDeadTriggeredAbility> {

	public KamiOfTheHonoredDeadTriggeredAbility() {
		super(Constants.Zone.BATTLEFIELD, new KamiOfTheHonoredDeadGainLifeEffect());
	}

	public KamiOfTheHonoredDeadTriggeredAbility(final KamiOfTheHonoredDeadTriggeredAbility effect) {
		super(effect);
	}

	@Override
	public KamiOfTheHonoredDeadTriggeredAbility copy() {
		return new KamiOfTheHonoredDeadTriggeredAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(this.sourceId)) {
                        this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                        return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever {this} is dealt damage, " + super.getRule();
	}
}


class KamiOfTheHonoredDeadGainLifeEffect extends OneShotEffect<KamiOfTheHonoredDeadGainLifeEffect> {

        public KamiOfTheHonoredDeadGainLifeEffect() {
            super(Outcome.GainLife);
            staticText = "you gain that much life";
        }

	public KamiOfTheHonoredDeadGainLifeEffect(final KamiOfTheHonoredDeadGainLifeEffect effect) {
		super(effect);
	}

	@Override
	public KamiOfTheHonoredDeadGainLifeEffect copy() {
		return new KamiOfTheHonoredDeadGainLifeEffect(this);
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