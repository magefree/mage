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

package mage.abilities.effects;

import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldEffect extends ReplacementEffectImpl {

	protected Effects baseEffects = new Effects(null);

	public EntersBattlefieldEffect(Effect baseEffect) {
		super(Duration.OneShot, baseEffect.getOutcome());
		this.baseEffects.add(baseEffect);
	}

	@Override
	public boolean applies(GameEvent event, Game game) {
		if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(this.source.getSourceId())) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
			if (zEvent.getToZone() == Zone.BATTLEFIELD)
				return true;
		}
		return false;
	}

	@Override
	public boolean apply(Game game) {
		for (Effect effect: baseEffects) {
			if (effect instanceof ContinuousEffect) {
				game.addEffect((ContinuousEffect) effect);
			}
			else
				effect.apply(game);
		}
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Game game) {
		return apply(game);
	}


	@Override
	public void setSource(Ability source) {
		this.source = source;
		baseEffects.setSource(source);
	}

	@Override
	public String getText() {
		return "When {this} enters the battlefield, " + baseEffects.getText();
	}

	@Override
	public boolean apply(Layer layer, SubLayer sublayer, Game game) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
