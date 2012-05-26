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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.sets.tokens.EmptyToken;
import mage.util.CardUtil;

import java.util.UUID;
import mage.MageObject;

/**
 *
 * @author nantuko
 */
public class MyrPropagator extends CardImpl<MyrPropagator> {

    public MyrPropagator(UUID ownerId) {
        super(ownerId, 182, "Myr Propagator", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Myr");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}, {tap}: Put a token that's a copy of Myr Propagator onto the battlefield.
		Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new MyrPropagatorCreateTokenEffect(), new GenericManaCost(3));
		ability.addCost(new TapSourceCost());
		this.addAbility(ability);
    }

    public MyrPropagator(final MyrPropagator card) {
        super(card);
    }

    @Override
    public MyrPropagator copy() {
        return new MyrPropagator(this);
    }
}

class MyrPropagatorCreateTokenEffect extends OneShotEffect<MyrPropagatorCreateTokenEffect> {

	public MyrPropagatorCreateTokenEffect() {
		super(Constants.Outcome.PutCreatureInPlay);
		this.staticText = "Put a token that's a copy of Myr Propagator onto the battlefield";
	}

	public MyrPropagatorCreateTokenEffect(final MyrPropagatorCreateTokenEffect effect) {
		super(effect);
	}

	@Override
	public MyrPropagatorCreateTokenEffect copy() {
		return new MyrPropagatorCreateTokenEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		MageObject thisCard = game.getLastKnownInformation(source.getSourceId(), Constants.Zone.BATTLEFIELD);
		if (thisCard != null && thisCard instanceof Permanent) {
			EmptyToken token = new EmptyToken();
			CardUtil.copyTo(token).from((Permanent)thisCard);
			token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
			return true;
		} else { // maybe it's token
			Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
			if (permanent != null) {
				EmptyToken token = new EmptyToken();
				CardUtil.copyTo(token).from(permanent);
				token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
				return true;
			}
		}
		return false;
	}

}
