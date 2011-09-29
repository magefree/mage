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

package mage.game.permanent.token;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageObjectImpl;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;

public class Token extends MageObjectImpl<Token> {

	protected String description;
    private UUID lastAddedTokenId;

	public Token(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Token(String name, String description, ObjectColor color, List<String> subtype, int power, int toughness, Abilities abilities) {
		this(name, description);
		this.cardType.add(CardType.CREATURE);
		this.color = color.copy();
		this.subtype = subtype;
		this.power.setValue(power);
		this.toughness.setValue(toughness);
		if (abilities != null) {
			this.abilities = abilities.copy();
		}
	}

	public Token(final Token token) {
		super(token);
		this.description = token.description;
	}

	public String getDescription() {
		return description;
	}

    public UUID getLastAddedToken() {
        return lastAddedTokenId;
    }

    public void addAbility(Ability ability) {
		ability.setSourceId(this.getId());
		abilities.add(ability);
	}

	@Override
	public Token copy() {
		return new Token(this);
	}

	public boolean putOntoBattlefield(int amount, Game game, UUID sourceId, UUID controllerId) {
		Card source = game.getCard(sourceId);
		String setCode = source != null ? source.getExpansionSetCode() : null;
		GameEvent event = GameEvent.getEvent(EventType.CREATE_TOKEN, null, sourceId, controllerId, amount);
        if (!game.replaceEvent(event)) {
            amount = event.getAmount();
            for (int i = 0; i < amount; i++) {
                PermanentToken permanent = new PermanentToken(this, controllerId, setCode);
                game.getBattlefield().addPermanent(permanent);
                this.lastAddedTokenId = permanent.getId();
                permanent.entersBattlefield(sourceId, game);
                game.applyEffects();
                game.fireEvent(new ZoneChangeEvent(permanent, controllerId, Zone.OUTSIDE, Zone.BATTLEFIELD));
            }
            return true;
        }
        return false;
	}

}
