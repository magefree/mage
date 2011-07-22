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

package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.game.events.GameEvent.EventType;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Viserion
 */
public class PhyrexianVatmother extends CardImpl<PhyrexianVatmother> {

    public PhyrexianVatmother (UUID ownerId) {
        super(ownerId, 52, "Phyrexian Vatmother", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Horror");
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new PoisonControllerEffect(1)));
    }

    public PhyrexianVatmother (final PhyrexianVatmother card) {
        super(card);
    }

    @Override
    public PhyrexianVatmother copy() {
        return new PhyrexianVatmother(this);
    }

}

class PoisonControllerEffect extends OneShotEffect<PoisonControllerEffect> {

	protected int amount;

	public PoisonControllerEffect(int amount) {
		super(Outcome.Damage);
		this.amount = amount;
		staticText = "you get " + amount + " poison counter(s)";

	}

	public PoisonControllerEffect(final PoisonControllerEffect effect) {
		super(effect);
		this.amount = effect.amount;
	}

	@Override
	public PoisonControllerEffect copy() {
		return new PoisonControllerEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		if (player != null) {
			player.getCounters().addCounter(CounterType.POISON.createInstance(amount));
			return true;
		}
		return false;
	}

}
