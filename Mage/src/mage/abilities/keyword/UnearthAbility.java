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

package mage.abilities.keyword;

import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.TimingRule;
import mage.Constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.GainAbilitySourceEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class UnearthAbility extends ActivatedAbilityImpl {

	protected boolean unearthed = false;

	public UnearthAbility(ManaCosts costs) {
		super(Zone.GRAVEYARD, new UnearthEffect(), costs);
		this.timing = TimingRule.SORCERY;
		this.effects.add(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield));
		this.effects.add(new GainAbilitySourceEffect(new OnEventTriggeredAbility(EventType.END_TURN_STEP_PRE, "beginning of the end step", new ExileSourceEffect()), Duration.WhileOnBattlefield));
		this.effects.add(new UnearthLeavesBattlefieldEffect());
	}

	public boolean isUnearthed() {
		return unearthed;
	}

	@Override
	public String getRule() {
		return "Unearth " + super.getRule();
	}

}

class UnearthEffect extends OneShotEffect {

	public UnearthEffect() {
		super(Outcome.PutCreatureInPlay);
	}

	@Override
	public boolean apply(Game game) {
		Player player = game.getPlayer(this.source.getControllerId());
		Card card = player.getGraveyard().get(this.source.getSourceId());
		if (card != null) {
			player.putOntoBattlefield(card, game);
			return true;
		}
		return false;
	}

	@Override
	public String getText() {
		return "Return this card from your graveyard to the battlefield";
	}

}

class UnearthLeavesBattlefieldEffect extends ReplacementEffectImpl {


	public UnearthLeavesBattlefieldEffect() {
		super(Duration.WhileOnBattlefield, Outcome.Exile);
	}

	@Override
	public boolean applies(GameEvent event, Game game) {
		if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(this.source.getSourceId())) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
			if (zEvent.getFromZone() == Zone.BATTLEFIELD)
				return true;
		}
		return false;
	}

	@Override
	public boolean apply(Game game) {
		ExileSourceEffect effect = new ExileSourceEffect();
		effect.setSource(source);
		return effect.apply(game);
	}

	@Override
	public boolean replaceEvent(GameEvent event, Game game) {
		return apply(game);
	}

	@Override
	public String getText() {
		return "When {this} leaves the battlefield, exile {this}";
	}
}