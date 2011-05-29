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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StormtideLeviathan extends CardImpl<StormtideLeviathan> {

	public StormtideLeviathan(UUID ownerId) {
		super(ownerId, 74, "Stormtide Leviathan", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}{U}{U}");
		this.expansionSetCode = "M11";
		this.subtype.add("Leviathan");
		this.color.setBlue(true);
		this.power = new MageInt(8);
		this.toughness = new MageInt(8);

		this.addAbility(new IslandwalkAbility());
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StormtideLeviathanEffect()));
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StormtideLeviathanEffect2()));

	}

	public StormtideLeviathan(final StormtideLeviathan card) {
		super(card);
	}

	@Override
	public StormtideLeviathan copy() {
		return new StormtideLeviathan(this);
	}

}

class StormtideLeviathanEffect extends ContinuousEffectImpl<StormtideLeviathanEffect> {

	public StormtideLeviathanEffect() {
		super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
	}

	public StormtideLeviathanEffect(final StormtideLeviathanEffect effect) {
		super(effect);
	}

	@Override
	public StormtideLeviathanEffect copy() {
		return new StormtideLeviathanEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		for (Permanent permanent: game.getBattlefield().getActivePermanents(new FilterLandPermanent(), source.getControllerId(), game)) {
			if (!permanent.getSubtype().contains("Island"))
				permanent.getSubtype().add("Island");
		}
		return true;
	}

	@Override
	public String getText(Ability source) {
		return "All lands are Islands in addition to their other types";
	}
}

class StormtideLeviathanEffect2 extends ReplacementEffectImpl<StormtideLeviathanEffect2> {

	private static IslandwalkAbility islandwalk = new IslandwalkAbility();

	public StormtideLeviathanEffect2() {
		super(Duration.WhileOnBattlefield, Outcome.Detriment);
	}

	public StormtideLeviathanEffect2(final StormtideLeviathanEffect2 effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public StormtideLeviathanEffect2 copy() {
		return new StormtideLeviathanEffect2(this);
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		return true;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if (event.getType() == EventType.DECLARE_ATTACKER) {
			Permanent permanent = game.getPermanent(event.getSourceId());
			if (permanent != null) {
				Player player = game.getPlayer(source.getControllerId());
				if (player.getInRange().contains(permanent.getControllerId())) {
					if (!(permanent.getAbilities().containsKey(FlyingAbility.getInstance().getId()) ||
						permanent.getAbilities().contains(islandwalk)))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "Creatures without flying or islandwalk can't attack";
	}

}
