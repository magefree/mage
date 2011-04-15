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

package mage.sets.worldwake;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author Viserion
 */
public class Groundswell extends CardImpl<Groundswell> {

	public Groundswell(UUID ownerId) {
		super(ownerId, 104, "Groundswell", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
		this.expansionSetCode = "WWK";
		this.color.setGreen(true);
		
		this.getSpellAbility().addTarget(new TargetCreaturePermanent());
		this.getSpellAbility().addEffect(new GroundswellEffect(Duration.EndOfTurn));
		
		this.watchers.add(new GroundswellWatcher(ownerId));
	}

	public Groundswell(final Groundswell card) {
		super(card);
	}

	@Override
	public Groundswell copy() {
		return new Groundswell(this);
	}
}

class GroundswellWatcher extends WatcherImpl<GroundswellWatcher> {

	public GroundswellWatcher(UUID controllerId) {
		super("LandPlayed", controllerId);
	}

	public GroundswellWatcher(final GroundswellWatcher watcher) {
		super(watcher);
	}

	@Override
	public GroundswellWatcher copy() {
		return new GroundswellWatcher(this);
	}

	@Override
	public void watch(GameEvent event, Game game) {
		if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone() == Zone.BATTLEFIELD) {
			Permanent permanent = game.getPermanent(event.getTargetId());
			if (permanent.getCardType().contains(CardType.LAND) && permanent.getControllerId().equals(this.controllerId)) {
				condition = true;
			}
		}
	}
}

class GroundswellEffect extends ContinuousEffectImpl<GroundswellEffect> {

	public GroundswellEffect(Duration duration) {
		super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
	}

	public GroundswellEffect(final GroundswellEffect effect) {
		super(effect);
	}

	@Override
	public GroundswellEffect copy() {
		return new GroundswellEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Watcher watcher = game.getState().getWatchers().get(source.getControllerId(), "LandPlayed");
		Permanent target = (Permanent) game.getPermanent(source.getFirstTarget());
		if (target != null) {
			if (watcher != null && watcher.conditionMet()) {
				target.addPower(4);
				target.addToughness(4);
			}
			else{
				target.addPower(2);
				target.addToughness(2);
			}
			return true;
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "Target creature gets +2/+2 until end of turn.\nLandfall - If you had a land enter the battlefield under your control this turn, that creature gets +4/+4 until end of turn instead";
	}
}
