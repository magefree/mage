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
package mage.sets.scarsofmirrodin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class LeoninArbiter extends CardImpl<LeoninArbiter> {

	public LeoninArbiter(UUID ownerId) {
		super(ownerId, 14, "Leonin Arbiter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Cat");
		this.subtype.add("Cleric");

		this.color.setWhite(true);
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeoninArbiterReplacementEffect()));
	}

	public LeoninArbiter(final LeoninArbiter card) {
		super(card);
	}

	@Override
	public LeoninArbiter copy() {
		return new LeoninArbiter(this);
	}
}

class LeoninArbiterReplacementEffect extends ReplacementEffectImpl<LeoninArbiterReplacementEffect> {

	private static final String effectText = "Players can't search libraries. Any player may pay {2} for that player to ignore this effect until end of turn";
	private List<UUID> paidPlayers = new ArrayList<UUID>();

	LeoninArbiterReplacementEffect ( ) {
		super(Duration.WhileOnBattlefield, Outcome.Neutral);
		staticText = effectText;
	}

	LeoninArbiterReplacementEffect ( LeoninArbiterReplacementEffect effect ) {
		super(effect);
		this.paidPlayers = effect.paidPlayers;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		if ( event.getType() == EventType.SEARCH_LIBRARY && !paidPlayers.contains(event.getPlayerId()) ) {
			Player player = game.getPlayer(event.getPlayerId());

			if ( player != null ) {
				ManaCostsImpl arbiterTax = new ManaCostsImpl("{2}");
				if ( arbiterTax.canPay(source.getSourceId(), event.getPlayerId(), game) &&
					 player.chooseUse(Outcome.Neutral, "Pay {2} to search your library?", game) )
				{
					arbiterTax.pay(source, game, this.getId(), event.getPlayerId(), false);

					if ( arbiterTax.isPaid() ) {
						paidPlayers.add(event.getPlayerId());
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if ( event.getType() == EventType.SEARCH_LIBRARY ) {
			return true;
		}
		if ( event.getType() == EventType.END_TURN_STEP_POST ) {
			this.paidPlayers.clear();
		}
		return false;
	}
	
	@Override
	public LeoninArbiterReplacementEffect copy() {
		return new LeoninArbiterReplacementEffect(this);
	}

}
