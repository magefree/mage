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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class GlimmerpointStag extends CardImpl<GlimmerpointStag> {
	
	public GlimmerpointStag(UUID ownerId) {
		super(ownerId, 9, "Glimmerpoint Stag", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Elk");

		this.color.setWhite(true);
		this.power = new MageInt(3);
		this.toughness = new MageInt(3);
		
		this.addAbility(VigilanceAbility.getInstance());
		Target etbTarget = new TargetPermanent();
		etbTarget.setRequired(true);
		Ability etbAbility = new EntersBattlefieldTriggeredAbility(new GlimmerpointStagEffect());
		etbAbility.addTarget(etbTarget);
		this.addAbility(etbAbility);
	}

	public GlimmerpointStag(final GlimmerpointStag card) {
		super(card);
	}

	@Override
	public GlimmerpointStag copy() {
		return new GlimmerpointStag(this);
	}
}

class GlimmerpointStagEffect extends OneShotEffect<GlimmerpointStagEffect> {

	private static final String effectText = "exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step";

	GlimmerpointStagEffect ( ) {
		super(Outcome.Benefit);
	}
	
	GlimmerpointStagEffect(GlimmerpointStagEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			if (permanent.moveToExile(source.getSourceId(), "Glimmerpoint Stag Exile", source.getId(), game)) {
				//create delayed triggered ability
				GlimmerpointStagDelayedTriggeredAbility delayedAbility = new GlimmerpointStagDelayedTriggeredAbility(source.getSourceId());
				delayedAbility.setSourceId(source.getSourceId());
				delayedAbility.setControllerId(source.getControllerId());
				game.addDelayedTriggeredAbility(delayedAbility);
				return true;
			}
		}
		return false;
	}

	@Override
	public GlimmerpointStagEffect copy() {
		return new GlimmerpointStagEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return effectText;
	}
}

class GlimmerpointStagDelayedTriggeredAbility extends DelayedTriggeredAbility<GlimmerpointStagDelayedTriggeredAbility> {

	GlimmerpointStagDelayedTriggeredAbility ( UUID exileId ) {
		super(new ReturnFromExileEffect(exileId, Zone.BATTLEFIELD));
	}

	GlimmerpointStagDelayedTriggeredAbility(GlimmerpointStagDelayedTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.END_TURN_STEP_PRE) {
			return true;
		}
		return false;
	}
	@Override
	public GlimmerpointStagDelayedTriggeredAbility copy() {
		return new GlimmerpointStagDelayedTriggeredAbility(this);
	}
}