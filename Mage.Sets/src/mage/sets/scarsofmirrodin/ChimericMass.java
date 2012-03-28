/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.Constants.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChimericMass extends CardImpl<ChimericMass> {

	public ChimericMass(UUID ownerId) {
		super(ownerId, 141, "Chimeric Mass", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{X}");
		this.expansionSetCode = "SOM";
        this.addAbility(new EntersBattlefieldAbility(new ChimericMassEffect(), "{this} enters the battlefield with X charge counters on it"));
		this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new ChimericMassToken(), "", Duration.EndOfTurn), new GenericManaCost(1)));
	}

	public ChimericMass(final ChimericMass card) {
		super(card);
	}

	@Override
	public ChimericMass copy() {
		return new ChimericMass(this);
	}

}

class ChimericMassEffect extends OneShotEffect<ChimericMassEffect> {
    public ChimericMassEffect() {
        super(Outcome.Benefit);
    }

    public ChimericMassEffect(final ChimericMassEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object value = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
        if (value != null && value instanceof Ability) {
            Ability castAbility = (Ability)value;
            int amount = castAbility.getManaCostsToPay().getX();
            Permanent p = game.getPermanent(source.getSourceId());
            if (p != null) {
                p.addCounters(CounterType.CHARGE.createInstance(amount), game);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public ChimericMassEffect copy() {
        return new ChimericMassEffect(this);
    }
}

class ChimericMassToken extends Token {

	public ChimericMassToken() {
		super("", "Construct artifact creature with \"This creature's power and toughness are each equal to the number of charge counters on it.\"");
		cardType.add(CardType.CREATURE);
		subtype.add("Construct");
		power = MageInt.EmptyMageInt;
		toughness = MageInt.EmptyMageInt;
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChimericMassTokenEffect()));
	}
}

class ChimericMassTokenEffect extends ContinuousEffectImpl<ChimericMassTokenEffect> {

	private DynamicValue counters = new CountersCount(CounterType.CHARGE);
	
	public ChimericMassTokenEffect() {
		super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
	}

	public ChimericMassTokenEffect(final ChimericMassTokenEffect effect) {
		super(effect);
	}

	@Override
	public ChimericMassTokenEffect copy() {
		return new ChimericMassTokenEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent token = game.getPermanent(source.getSourceId());
		if (token != null) {
			int count = counters.calculate(game, source);
			token.getPower().setValue(count);
			token.getToughness().setValue(count);
			return true;
		}
		return false;
	}

}