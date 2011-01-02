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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.players.Players;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class PulseTracker extends CardImpl<PulseTracker> {

	public PulseTracker(UUID ownerId) {
		super(ownerId, 62, "Pulse Tracker", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
		this.expansionSetCode = "WWK";
		this.subtype.add("Vampire");
		this.subtype.add("Rogue");

		this.color.setBlack(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);

		this.addAbility(new PulseTrackerTriggeredAbility());
	}

	public PulseTracker(final PulseTracker card) {
		super(card);
	}

	@Override
	public PulseTracker copy() {
		return new PulseTracker(this);
	}
}

class PulseTrackerTriggeredAbility extends TriggeredAbilityImpl<PulseTrackerTriggeredAbility> {

	public PulseTrackerTriggeredAbility() {
		super(Zone.BATTLEFIELD, new PulseTrackerLoseLifeEffect(), false);
	}

	public PulseTrackerTriggeredAbility(final PulseTrackerTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public PulseTrackerTriggeredAbility copy() {
		return new PulseTrackerTriggeredAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever Pulse Tracker attacks, each opponent loses 1 life.";
	}
}

class PulseTrackerLoseLifeEffect extends OneShotEffect<PulseTrackerLoseLifeEffect> {

	PulseTrackerLoseLifeEffect ( ) {
		super(Outcome.Damage);
	}

	PulseTrackerLoseLifeEffect ( PulseTrackerLoseLifeEffect effect ) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Players players = game.getPlayers();

		for ( Player player : players.values() ) {
			if ( !player.getId().equals(source.getControllerId()) ) {
				player.loseLife(1, game);
			}
		}

		return true;
	}

	@Override
	public PulseTrackerLoseLifeEffect copy() {
		return new PulseTrackerLoseLifeEffect(this);
	}

}
