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
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentToken extends PermanentImpl<PermanentToken> {

	protected Token token;
	
	public PermanentToken(Token token, UUID controllerId) {
		super(controllerId, controllerId, token.getName());
		this.token = token;
		copyFromToken(token);
	}

	public PermanentToken(final PermanentToken permanent) {
		super(permanent);
		this.token = permanent.token.copy();
	}

	@Override
	public void reset(Game game) {
		Token copy = token.copy();
		copyFromToken(copy);
		super.reset(game);
	}

	protected void copyFromToken(Token token) {
		this.name = token.getName();
		this.abilities.clear();
		for (Ability ability: token.getAbilities()) {
			this.addAbility(ability);
		}
		this.cardType = token.getCardType();
		this.color = token.getColor();
		this.power = token.getPower();
		this.toughness = token.getToughness();
		this.subtype = token.getSubtype();
	}

	@Override
	public boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag) {
		if (!game.replaceEvent(new ZoneChangeEvent(this, this.getControllerId(), Zone.BATTLEFIELD, zone))) {
			if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
				game.fireEvent(new ZoneChangeEvent(this, this.getControllerId(), Zone.BATTLEFIELD, zone));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
		if (!game.replaceEvent(new ZoneChangeEvent(this, sourceId, this.getControllerId(), Zone.BATTLEFIELD, Zone.EXILED))) {
			if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
				game.fireEvent(new ZoneChangeEvent(this, sourceId, this.getControllerId(), Zone.BATTLEFIELD, Zone.EXILED));
				return true;
			}
		}
		return false;
	}

	@Override
	public String getArt() {
		return "";
	}

	@Override
	public void setArt(String art) { }

	public Token getToken() {
		return token;
	}

	@Override
	public PermanentToken copy() {
		return new PermanentToken(this);
	}

}
