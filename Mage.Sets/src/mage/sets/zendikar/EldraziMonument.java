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

package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EldraziMonument extends CardImpl<EldraziMonument> {

	public EldraziMonument(UUID ownerId) {
		super(ownerId, 199, "Eldrazi Monument", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{5}");
		this.expansionSetCode = "ZEN";
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, new FilterCreaturePermanent(), false)));
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent())));
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent())));
		this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new EldraziMonumentEffect()));
	}

	public EldraziMonument(final EldraziMonument card) {
		super(card);
	}

	@Override
	public EldraziMonument copy() {
		return new EldraziMonument(this);
	}

}

class EldraziMonumentEffect extends OneShotEffect<EldraziMonumentEffect> {

	public EldraziMonumentEffect() {
		super(Outcome.Sacrifice);
		staticText = "sacrifice a creature. If you can't, sacrifice {this}";
	}

	public EldraziMonumentEffect(final EldraziMonumentEffect ability) {
		super(ability);
	}

	@Override
	public EldraziMonumentEffect copy() {
		return new EldraziMonumentEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		TargetControlledPermanent target = new TargetControlledCreaturePermanent();
		target.setRequired(true);
		Player player = game.getPlayer(source.getControllerId());
		if (target.canChoose(source.getControllerId(), game)) {
			player.choose(this.outcome, target, source.getSourceId(), game);
			Permanent permanent = game.getPermanent(target.getFirstTarget());
			if (permanent != null) {
				return permanent.sacrifice(source.getSourceId(), game);
			}
		}
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (permanent != null) {
			return permanent.sacrifice(source.getSourceId(), game);
		}
		return false;

	}

}

