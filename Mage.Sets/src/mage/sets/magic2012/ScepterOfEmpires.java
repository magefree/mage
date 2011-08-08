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
package mage.sets.magic2012;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

import java.util.UUID;

/**
 * @author nantuko
 */
public class ScepterOfEmpires extends CardImpl<ScepterOfEmpires> {

	public ScepterOfEmpires(UUID ownerId) {
		super(ownerId, 216, "Scepter of Empires", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
		this.expansionSetCode = "M12";

		// {tap}: Scepter of Empires deals 1 damage to target player. It deals 3 damage to that player instead if you control artifacts named Crown of Empires and Throne of Empires.
		Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ScepterOfEmpiresEffect(), new GenericManaCost(0));
		ability.addCost(new TapSourceCost());
		ability.addTarget(new TargetCreatureOrPlayer());
		this.addAbility(ability);
	}

	public ScepterOfEmpires(final ScepterOfEmpires card) {
		super(card);
	}

	@Override
	public ScepterOfEmpires copy() {
		return new ScepterOfEmpires(this);
	}
}

class ScepterOfEmpiresEffect extends OneShotEffect<ScepterOfEmpiresEffect> {

	public ScepterOfEmpiresEffect() {
		super(Constants.Outcome.PutCreatureInPlay);
		staticText = "Scepter of Empires deals 1 damage to target player. It deals 3 damage to that player instead if you control artifacts named Crown of Empires and Throne of Empires";
	}

	public ScepterOfEmpiresEffect(ScepterOfEmpiresEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		boolean throne = false;
		boolean crown = false;
		for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
			if (permanent.getName().equals("Throne of Empires")) {
				throne = true;
			} else if (permanent.getName().equals("Crown of Empires")) {
				crown = true;
			}
			if (throne && crown) break;
		}

		int amount = throne && crown ? 3 : 1;
		Permanent permanent = game.getPermanent(targetPointer.getFirst(source));
		if (permanent != null) {
			permanent.damage(amount, source.getSourceId(), game, true, false);
			return true;
		}
		Player player = game.getPlayer(targetPointer.getFirst(source));
		if (player != null) {
			player.damage(amount, source.getSourceId(), game, false, true);
			return true;
		}
		return false;
	}

	@Override
	public ScepterOfEmpiresEffect copy() {
		return new ScepterOfEmpiresEffect(this);
	}
}
