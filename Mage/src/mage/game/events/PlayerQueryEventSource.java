/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.game.events;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.ActivatedAbility;
import mage.abilities.TriggeredAbilities;
import mage.cards.Card;
import mage.cards.Cards;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerQueryEventSource implements EventSource<PlayerQueryEvent>, Serializable {

	protected final EventDispatcher<PlayerQueryEvent> dispatcher = new EventDispatcher<PlayerQueryEvent>() {};

	@Override
	public void addListener(Listener<PlayerQueryEvent> listener) {
		dispatcher.addListener(listener);
	}

	public void ask(UUID playerId, String message) {
		dispatcher.fireEvent(PlayerQueryEvent.askEvent(playerId, message));
	}

	public void select(UUID playerId, String message) {
		dispatcher.fireEvent(PlayerQueryEvent.selectEvent(playerId, message));
	}

	public void chooseAbility(UUID playerId, String message, Collection<? extends ActivatedAbility> choices) {
		dispatcher.fireEvent(PlayerQueryEvent.chooseAbilityEvent(playerId, message, choices));
	}

	public void target(UUID playerId, String message, Set<UUID> targets, boolean required) {
		dispatcher.fireEvent(PlayerQueryEvent.targetEvent(playerId, message, targets, required));
	}

	public void target(UUID playerId, String message, Cards cards, boolean required) {
		dispatcher.fireEvent(PlayerQueryEvent.targetEvent(playerId, message, cards, required));
	}

	public void target(UUID playerId, String message, Cards cards) {
		dispatcher.fireEvent(PlayerQueryEvent.lookEvent(playerId, message, cards));
	}

	public void target(UUID playerId, String message, TriggeredAbilities abilities, boolean required) {
		dispatcher.fireEvent(PlayerQueryEvent.targetEvent(playerId, message, abilities, required));
	}

	public void playMana(UUID playerId, String message) {
		dispatcher.fireEvent(PlayerQueryEvent.playManaEvent(playerId, message));
	}

	public void amount(UUID playerId, String message, int min, int max) {
		dispatcher.fireEvent(PlayerQueryEvent.amountEvent(playerId, message, min, max));
	}

	public void choose(UUID playerId, String message, Set<String> choices) {
		dispatcher.fireEvent(PlayerQueryEvent.chooseEvent(playerId, message, choices));
	}

	public void playXMana(UUID playerId, String message) {
		dispatcher.fireEvent(PlayerQueryEvent.playXManaEvent(playerId, message));
	}

	public void pickCard(UUID playerId, String message, List<Card> booster) {
		dispatcher.fireEvent(PlayerQueryEvent.pickCard(playerId, message, booster));
	}

}
