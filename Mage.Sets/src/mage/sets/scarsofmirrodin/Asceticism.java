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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author ayratn
 */
public class Asceticism extends CardImpl<Asceticism> {

	private static final FilterStackObject filter = new FilterStackObject("spells or abilities your opponents control");

	static {
		filter.setTargetController(Constants.TargetController.OPPONENT);
	}

	public Asceticism(UUID ownerId) {
		super(ownerId, 110, "Asceticism", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");
		this.expansionSetCode = "SOM";
		this.color.setGreen(true);
		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new AsceticismEffect(filter, Constants.Duration.WhileOnBattlefield)));
		Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl("{1}{G}"));
		ability.addTarget(new TargetCreaturePermanent());
		this.addAbility(ability);
	}

	public Asceticism(final Asceticism card) {
		super(card);
	}

	@Override
	public Asceticism copy() {
		return new Asceticism(this);
	}

}

class AsceticismEffect extends ReplacementEffectImpl<AsceticismEffect> {

	private FilterStackObject filterSource;

	public AsceticismEffect(FilterStackObject filterSource, Constants.Duration duration) {
		super(duration, Constants.Outcome.Benefit);
		this.filterSource = filterSource;
	}

	public AsceticismEffect(final AsceticismEffect effect) {
		super(effect);
		this.filterSource = effect.filterSource.copy();
	}

	@Override
	public AsceticismEffect copy() {
		return new AsceticismEffect(this);
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
		if (event.getType() == GameEvent.EventType.TARGET) {
			Permanent permanent = game.getBattlefield().getPermanent(event.getTargetId());
			if (permanent != null && permanent.getControllerId().equals(source.getControllerId())
					&& permanent.getCardType().contains(CardType.CREATURE)) {
				StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
				if (sourceObject != null && filterSource.match(sourceObject, source.getControllerId(), game)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "Creatures you control can't be the targets of spells or abilities your opponents control";
	}

}

