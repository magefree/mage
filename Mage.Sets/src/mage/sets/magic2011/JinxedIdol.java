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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class JinxedIdol extends CardImpl<JinxedIdol> {

	public JinxedIdol(UUID ownerId) {
		super(ownerId, 208, "Jinxed Idol", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
		this.expansionSetCode = "M11";
		this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new DamageControllerEffect(2)));
		Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JinxedIdolEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
		ability.addTarget(new TargetOpponent());
		this.addAbility(ability);
	}

	public JinxedIdol(final JinxedIdol card) {
		super(card);
	}

	@Override
	public JinxedIdol copy() {
		return new JinxedIdol(this);
	}

}

class JinxedIdolEffect extends ContinuousEffectImpl<JinxedIdolEffect> {

	public JinxedIdolEffect() {
		super(Duration.WhileOnBattlefield, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
	}

	public JinxedIdolEffect(final JinxedIdolEffect effect) {
		super(effect);
	}

	@Override
	public JinxedIdolEffect copy() {
		return new JinxedIdolEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (permanent != null) {
			return permanent.changeControllerId(source.getFirstTarget(), game);
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "Target opponent gains control of Jinxed Idol";
	}
}
