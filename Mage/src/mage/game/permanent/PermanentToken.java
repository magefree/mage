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

package mage.game.permanent;

import mage.game.permanent.token.Token;
import java.util.UUID;
import mage.Constants.Zone;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentToken extends PermanentImpl {

	protected Token token;
	
	public PermanentToken(Token token, UUID ownerId, UUID controllerId) {
		super(ownerId, controllerId, token.getName());
		this.token = token;
		reset();
	}

	@Override
	public void reset() {
		Token copy = token.copy();
		this.name = copy.getName();
		this.abilities = copy.getAbilities();
		this.abilities.setControllerId(this.controllerId);
		this.cardType = copy.getCardType();
		this.color = copy.getColor();
		this.power = copy.getPower();
		this.toughness = copy.getToughness();
		this.subtype = copy.getSubtype();
		super.reset();
	}

	@Override
	public boolean moveToZone(Zone zone, Game game, boolean sacrificed) {
		if (!game.replaceEvent(new ZoneChangeEvent(this.getId(), this.getControllerId(), Zone.BATTLEFIELD, zone))) {
			if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
				game.fireEvent(new ZoneChangeEvent(this.getId(), this.getControllerId(), Zone.BATTLEFIELD, zone));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean moveToExile(UUID exileId, String name, Game game) {
		if (!game.replaceEvent(new ZoneChangeEvent(this.getId(), this.getControllerId(), Zone.BATTLEFIELD, Zone.EXILED))) {
			if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
				game.fireEvent(new ZoneChangeEvent(this.getId(), this.getControllerId(), Zone.BATTLEFIELD, Zone.EXILED));
				return true;
			}
		}
		return false;
	}

}
