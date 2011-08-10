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
package mage.sets.magic2012;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.Players;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author nantuko
 */
public class Scrambleverse extends CardImpl<Scrambleverse> {

    public Scrambleverse(UUID ownerId) {
        super(ownerId, 153, "Scrambleverse", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{6}{R}{R}");
        this.expansionSetCode = "M12";

        this.color.setRed(true);

        // For each nonland permanent, choose a player at random. Then each player gains control of each permanent for which he or she was chosen. Untap those permanents.
		this.getSpellAbility().addEffect(new ScrambleverseEffect());
    }

    public Scrambleverse(final Scrambleverse card) {
        super(card);
    }

    @Override
    public Scrambleverse copy() {
        return new Scrambleverse(this);
    }
}

class ScrambleverseEffect extends OneShotEffect<ScrambleverseEffect> {

	public ScrambleverseEffect() {
		super(Constants.Outcome.Damage);
		staticText = "For each nonland permanent, choose a player at random. Then each player gains control of each permanent for which he or she was chosen. Untap those permanents";
	}

	public ScrambleverseEffect(ScrambleverseEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Random random = new Random();
		PlayerList players = game.getPlayerList();
		int count = players.size();
		if (count > 1) {
			FilterNonlandPermanent nonLand = new FilterNonlandPermanent();
			for (Permanent permanent : game.getBattlefield().getAllActivePermanents(nonLand)) {
				ContinuousEffect effect = new ScrambleverseControlEffect(players.get(random.nextInt(count)));
				effect.setTargetPointer(new FixedTarget(permanent.getId()));
				game.addEffect(effect, source);
			}
		}
		return true;
	}

	@Override
	public ScrambleverseEffect copy() {
		return new ScrambleverseEffect(this);
	}
}

class ScrambleverseControlEffect extends ContinuousEffectImpl<ScrambleverseControlEffect> {

	private UUID controllerId;

	public ScrambleverseControlEffect(UUID controllerId) {
		super(Constants.Duration.EndOfGame, Constants.Layer.ControlChangingEffects_2, Constants.SubLayer.NA, Constants.Outcome.GainControl);
		this.controllerId = controllerId;
	}

	public ScrambleverseControlEffect(final ScrambleverseControlEffect effect) {
		super(effect);
		this.controllerId = effect.controllerId;
	}

	@Override
	public ScrambleverseControlEffect copy() {
		return new ScrambleverseControlEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(targetPointer.getFirst(source));
		if (permanent != null && controllerId != null) {
			return permanent.changeControllerId(controllerId, game);
		}
		return false;
	}

	@Override
	public String getText(Mode mode) {
		return "Gain control of {this}";
	}
}
