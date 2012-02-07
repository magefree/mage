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

package mage.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;
import mage.game.GameState;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardsView extends HashMap<UUID, CardView> {

	public CardsView() {}

	public CardsView(Collection<? extends Card> cards) {
		for (Card card: cards) {
			this.put(card.getId(), new CardView(card));
		}
	}

	public CardsView ( Collection<? extends Ability> abilities, Game game ) {
		for ( Ability ability : abilities ) {
			Card sourceCard = null;
			switch ( ability.getZone() ) {
				case ALL:
				case EXILED:
				case GRAVEYARD:
					sourceCard = game.getCard(ability.getSourceId());
					break;
				case BATTLEFIELD:
                    sourceCard = game.getPermanent(ability.getSourceId());
                    if (sourceCard == null)
					    sourceCard = game.getLastKnownInformation(ability.getSourceId(), Zone.BATTLEFIELD);
					break;
			}
			if (sourceCard != null) {
				this.put(ability.getId(), new AbilityView(ability, sourceCard.getName(), new CardView(sourceCard)));
			}
		}
	}

	public CardsView(Collection<? extends Ability> abilities, GameState state) {
		for (Ability ability: abilities) {
			Card sourceCard = state.getPermanent(ability.getSourceId());
			if (sourceCard != null) {
				this.put(ability.getId(), new AbilityView(ability, sourceCard.getName(), new CardView(sourceCard)));
			}
		}
	}

}
