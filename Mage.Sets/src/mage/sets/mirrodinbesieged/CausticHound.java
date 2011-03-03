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

package mage.sets.mirrodinbesieged;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class CausticHound extends CardImpl<CausticHound> {

    public CausticHound (UUID ownerId) {
        super(ownerId, 40, "Caustic Hound", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Hound");
		this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
		this.addAbility(new PutIntoGraveFromBattlefieldTriggeredAbility(new CausticHoundEffect()));
    }

    public CausticHound (final CausticHound card) {
        super(card);
    }

    @Override
    public CausticHound copy() {
        return new CausticHound(this);
    }
}

class CausticHoundEffect extends OneShotEffect<CausticHoundEffect> {
	CausticHoundEffect() {
		super(Constants.Outcome.Damage);
	}

	CausticHoundEffect(final CausticHoundEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		for (UUID playerId : game.getPlayerList()) {
			Player p = game.getPlayer(playerId);
			if (p != null) {
				p.loseLife(4, game);
			}
		}
		return true;
	}

	@Override
	public CausticHoundEffect copy() {
		return new CausticHoundEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "each player loses 4 life";
	}
}