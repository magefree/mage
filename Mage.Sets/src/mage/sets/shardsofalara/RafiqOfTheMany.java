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

package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.sets.ShardsOfAlara;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RafiqOfTheMany extends CardImpl<RafiqOfTheMany> {

	public RafiqOfTheMany(UUID ownerId) {
		super(ownerId, "Rafiq of the Many", new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
		this.expansionSetId = ShardsOfAlara.getInstance().getId();
		this.color.setGreen(true);
		this.color.setWhite(true);
		this.color.setBlue(true);
		this.supertype.add("Legendary");
		this.subtype.add("Human");
		this.subtype.add("Knight");
		this.power = new MageInt(3);
		this.toughness = new MageInt(3);
		this.addAbility(new ExaltedAbility());
		this.addAbility(new RafiqOfTheManyAbility());
	}

	public RafiqOfTheMany(final RafiqOfTheMany card) {
		super(card);
	}

	@Override
	public RafiqOfTheMany copy() {
		return new RafiqOfTheMany(this);
	}

	@Override
	public String getArt() {
		return "115029_typ_reg_sty_010.jpg";
	}

}

class RafiqOfTheManyAbility extends TriggeredAbilityImpl<RafiqOfTheManyAbility> {

	public RafiqOfTheManyAbility() {
		super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
	}

	public RafiqOfTheManyAbility(final RafiqOfTheManyAbility ability) {
		super(ability);
	}

	@Override
	public RafiqOfTheManyAbility copy() {
		return new RafiqOfTheManyAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.DECLARED_ATTACKERS && game.getActivePlayerId().equals(this.controllerId) ) {
			if (game.getCombat().attacksAlone()) {
				this.addTarget(new TargetCreaturePermanent());
				this.targets.get(0).addTarget(game.getCombat().getAttackers().get(0), null, game);
				trigger(game, event.getPlayerId());
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever a creature you control attacks alone, it gains double strike until end of turn.";
	}

}
