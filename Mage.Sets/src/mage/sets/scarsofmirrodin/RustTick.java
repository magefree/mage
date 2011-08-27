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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class RustTick extends CardImpl<RustTick> {

	public RustTick(UUID ownerId) {
		super(ownerId, 198, "Rust Tick", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Insect");

		this.power = new MageInt(1);
		this.toughness = new MageInt(3);

		// You may choose not to untap Rust Tick during your untap step.
		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new RustTickSelfRestrictionEffect()));

		// {1}, {tap}: Tap target artifact. It doesn't untap during its controller's untap step for as long as Rust Tick remains tapped.
		Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RustTickTapTargetEffect(), new GenericManaCost(1));
		ability.addCost(new TapSourceCost());
		ability.addTarget(new TargetArtifactPermanent());
		this.addAbility(ability);

		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new RustTickRestrictionEffect()));
	}

	public RustTick(final RustTick card) {
		super(card);
	}

	@Override
	public RustTick copy() {
		return new RustTick(this);
	}
}

class RustTickTapTargetEffect extends TapTargetEffect {

	public RustTickTapTargetEffect() {
		super();
		staticText = "Tap target artifact. It doesn't untap during its controller's untap step for as long as Rust Tick remains tapped";
	}

	public RustTickTapTargetEffect(final RustTickTapTargetEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent rustTick = game.getPermanent(source.getSourceId());
		if (rustTick != null) rustTick.clearConnectedCards();
		for (UUID target : source.getTargets().get(0).getTargets()) {
			Permanent permanent = game.getPermanent(target);
			if (permanent != null) {
				rustTick.addConnectedCard(permanent.getId());
				permanent.tap(game);
			} else {
				return false;
			}
		}
		return true;
	}


	@Override
	public RustTickTapTargetEffect copy() {
		return new RustTickTapTargetEffect(this);
	}

	@Override
	public String getText(Mode mode) {
		return staticText;
	}
}

class RustTickRestrictionEffect extends RestrictionEffect<RustTickRestrictionEffect> {

	public RustTickRestrictionEffect() {
		super(Constants.Duration.WhileOnBattlefield);
	}

	public RustTickRestrictionEffect(final RustTickRestrictionEffect effect) {
		super(effect);
	}

	@Override
	public boolean applies(Permanent permanent, Ability source, Game game) {
		Permanent rustTick = game.getPermanent(source.getSourceId());
		if (rustTick != null && rustTick.isTapped()) {
			if (rustTick.getConnectedCards().size() > 0) {
				UUID target = rustTick.getConnectedCards().get(0);
				if (target != null && target.equals(permanent.getId())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean canBeUntapped(Permanent permanent, Game game) {
		return false;
	}

	@Override
	public RustTickRestrictionEffect copy() {
		return new RustTickRestrictionEffect(this);
	}

}

class RustTickSelfRestrictionEffect extends RestrictionEffect<RustTickSelfRestrictionEffect> {

	public RustTickSelfRestrictionEffect() {
		super(Constants.Duration.WhileOnBattlefield);
		staticText = "You may choose not to untap Rust Tick during your untap step";
	}

	public RustTickSelfRestrictionEffect(final RustTickSelfRestrictionEffect effect) {
		super(effect);
	}

	@Override
	public boolean applies(Permanent permanent, Ability source, Game game) {
		return permanent.getId().equals(source.getSourceId()) && permanent.isTapped();
	}

	@Override
	public boolean canBeUntapped(Permanent permanent, Game game) {
		Player player = game.getPlayer(permanent.getControllerId());
		return player != null && player.chooseUse(Constants.Outcome.Benefit, "Untap Rust Tick?", game);
	}

	@Override
	public RustTickSelfRestrictionEffect copy() {
		return new RustTickSelfRestrictionEffect(this);
	}

}
