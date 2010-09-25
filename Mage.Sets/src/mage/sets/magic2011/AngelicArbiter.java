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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AngelicArbiter extends CardImpl<AngelicArbiter> {

	public AngelicArbiter(UUID ownerId) {
		super(ownerId, "Angelic Arbiter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
		this.expansionSetCode = "M11";
		this.subtype.add("Angel");
		this.color.setWhite(true);
		this.power = new MageInt(5);
		this.toughness = new MageInt(6);

		this.addAbility(FlyingAbility.getInstance());
		this.watchers.add(new AngelicArbiterWatcher1(ownerId));
		this.watchers.add(new AngelicArbiterWatcher2(ownerId));
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AngelicArbiterEffect1()));
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AngelicArbiterEffect2()));
	}

	public AngelicArbiter(final AngelicArbiter card) {
		super(card);
	}

	@Override
	public AngelicArbiter copy() {
		return new AngelicArbiter(this);
	}

	@Override
	public String getArt() {
		return "129070_typ_reg_sty_010.jpg";
	}

}

class AngelicArbiterWatcher1 extends WatcherImpl<AngelicArbiterWatcher1> {

	public AngelicArbiterWatcher1(UUID controllerId) {
		super("CastSpell", controllerId);
	}

	public AngelicArbiterWatcher1(final AngelicArbiterWatcher1 watcher) {
		super(watcher);
	}

	@Override
	public AngelicArbiterWatcher1 copy() {
		return new AngelicArbiterWatcher1(this);
	}

	@Override
	public void watch(GameEvent event, Game game) {
		if (event.getType() == EventType.SPELL_CAST && game.getActivePlayerId().equals(event.getPlayerId()) && game.getOpponents(controllerId).contains(event.getPlayerId()))
			condition = true;
	}

}

class AngelicArbiterWatcher2 extends WatcherImpl<AngelicArbiterWatcher2> {

	public AngelicArbiterWatcher2(UUID controllerId) {
		super("Attacked", controllerId);
	}

	public AngelicArbiterWatcher2(final AngelicArbiterWatcher2 watcher) {
		super(watcher);
	}

	@Override
	public AngelicArbiterWatcher2 copy() {
		return new AngelicArbiterWatcher2(this);
	}

	@Override
	public void watch(GameEvent event, Game game) {
		if (event.getType() == EventType.DECLARED_ATTACKERS && game.getActivePlayerId().equals(event.getPlayerId()) && game.getOpponents(controllerId).contains(event.getPlayerId()))
			condition = true;
	}

}

class AngelicArbiterEffect1 extends ReplacementEffectImpl<AngelicArbiterEffect1> {

	public AngelicArbiterEffect1() {
		super(Duration.WhileOnBattlefield, Outcome.Benefit);
	}

	public AngelicArbiterEffect1(final AngelicArbiterEffect1 effect) {
		super(effect);
	}

	@Override
	public AngelicArbiterEffect1 copy() {
		return new AngelicArbiterEffect1(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		return true;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if (event.getType() == EventType.DECLARE_ATTACKER && game.getActivePlayerId().equals(event.getPlayerId()) && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
			Watcher watcher = game.getState().getWatchers().get(source.getControllerId(), "CastSpell");
			if (watcher != null && watcher.conditionMet())
				return true;
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "Each opponent who cast a spell this turn can't attack with creatures.";
	}

}

class AngelicArbiterEffect2 extends ReplacementEffectImpl<AngelicArbiterEffect2> {

	public AngelicArbiterEffect2() {
		super(Duration.WhileOnBattlefield, Outcome.Benefit);
	}

	public AngelicArbiterEffect2(final AngelicArbiterEffect2 effect) {
		super(effect);
	}

	@Override
	public AngelicArbiterEffect2 copy() {
		return new AngelicArbiterEffect2(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		return true;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if (event.getType() == EventType.CAST_SPELL && game.getActivePlayerId().equals(event.getPlayerId()) && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
			Watcher watcher = game.getState().getWatchers().get(source.getControllerId(), "Attacked");
			if (watcher != null && watcher.conditionMet())
				return true;
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "Each opponent who attacked with a creature this turn can't cast spells.";
	}

}
