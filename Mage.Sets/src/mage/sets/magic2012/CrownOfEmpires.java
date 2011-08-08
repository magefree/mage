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
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author nantuko
 */
public class CrownOfEmpires extends CardImpl<CrownOfEmpires> {

	public CrownOfEmpires(UUID ownerId) {
		super(ownerId, 203, "Crown of Empires", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
		this.expansionSetCode = "M12";

		// {3}, {tap}: Tap target creature. Gain control of that creature instead if you control artifacts named Scepter of Empires and Throne of Empires.
		Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CrownOfEmpiresEffect(), new GenericManaCost(3));
		ability.addTarget(new TargetCreaturePermanent());
		ability.addCost(new TapSourceCost());
		this.addAbility(ability);
	}

	public CrownOfEmpires(final CrownOfEmpires card) {
		super(card);
	}

	@Override
	public CrownOfEmpires copy() {
		return new CrownOfEmpires(this);
	}
}

class CrownOfEmpiresEffect extends OneShotEffect<CrownOfEmpiresEffect> {

	public CrownOfEmpiresEffect() {
		super(Constants.Outcome.Tap);
		staticText = "Tap target creature. Gain control of that creature instead if you control artifacts named Scepter of Empires and Throne of Empires";
	}

	public CrownOfEmpiresEffect(CrownOfEmpiresEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent target = game.getPermanent(targetPointer.getFirst(source));
		boolean scepter = false;
		boolean throne = false;
		for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
			if (permanent.getName().equals("Scepter of Empires")) {
				scepter = true;
			} else if (permanent.getName().equals("Throne of Empires")) {
				throne = true;
			}
			if (scepter && throne) break;
		}
		if (scepter && throne) {
			ContinuousEffect effect = new CrownOfEmpiresControlEffect();
			effect.setTargetPointer(new FixedTarget(target.getId()));
			game.getState().setValue(source.getSourceId().toString(), source.getControllerId());
			game.addEffect(effect, source);
		} else {
			target.tap(game);
		}
		return false;
	}

	@Override
	public CrownOfEmpiresEffect copy() {
		return new CrownOfEmpiresEffect(this);
	}
}

class CrownOfEmpiresControlEffect extends ContinuousEffectImpl<CrownOfEmpiresControlEffect> {

	public CrownOfEmpiresControlEffect() {
		super(Constants.Duration.EndOfGame, Constants.Layer.ControlChangingEffects_2, Constants.SubLayer.NA, Constants.Outcome.GainControl);
	}

	public CrownOfEmpiresControlEffect(final CrownOfEmpiresControlEffect effect) {
		super(effect);
	}

	@Override
	public CrownOfEmpiresControlEffect copy() {
		return new CrownOfEmpiresControlEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(targetPointer.getFirst(source));
		UUID controllerId = (UUID) game.getState().getValue(source.getSourceId().toString());
		if (permanent != null && controllerId != null) {
			return permanent.changeControllerId(controllerId, game);
		}
		return false;
	}

	@Override
	public String getText(Mode mode) {
		return "Gain control of {this}";
	}
}
