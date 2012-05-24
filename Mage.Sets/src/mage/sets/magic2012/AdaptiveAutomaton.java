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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.sets.Sets;

import java.util.UUID;

/**
 * @author nantuko
 */
public class AdaptiveAutomaton extends CardImpl<AdaptiveAutomaton> {

	public AdaptiveAutomaton(UUID ownerId) {
		super(ownerId, 201, "Adaptive Automaton", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
		this.expansionSetCode = "M12";
		this.subtype.add("Construct");

		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

		// As Adaptive Automaton enters the battlefield, choose a creature type.
		this.addAbility(new EntersBattlefieldAbility(new AdaptiveAutomatonEffect()));
		// Adaptive Automaton is the chosen type in addition to its other types.
		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new AdaptiveAutomatonAddSubtypeEffect()));
		// Other creatures you control of the chosen type get +1/+1.
		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new AdaptiveAutomatonBoostControlledEffect()));
	}

	public AdaptiveAutomaton(final AdaptiveAutomaton card) {
		super(card);
	}

	@Override
	public AdaptiveAutomaton copy() {
		return new AdaptiveAutomaton(this);
	}
}

class AdaptiveAutomatonEffect extends OneShotEffect<AdaptiveAutomatonEffect> {

	public AdaptiveAutomatonEffect() {
		super(Constants.Outcome.BoostCreature);
		staticText = "As {this} enters the battlefield, choose a creature type";
	}

	public AdaptiveAutomatonEffect(final AdaptiveAutomatonEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (player != null && permanent != null) {
			Choice typeChoice = new ChoiceImpl(true);
			typeChoice.setChoices(Sets.getCreatureTypes());
			while (!player.choose(Constants.Outcome.BoostCreature, typeChoice, game)) {
                game.debugMessage("player canceled choosing type. retrying.");
            }
			game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + typeChoice.getChoice());
		    game.getState().setValue(permanent.getId() + "_type", typeChoice.getChoice());
		}
		return false;
	}

	@Override
	public AdaptiveAutomatonEffect copy() {
		return new AdaptiveAutomatonEffect(this);
	}

}

class AdaptiveAutomatonAddSubtypeEffect extends ContinuousEffectImpl<AdaptiveAutomatonAddSubtypeEffect> {
	public AdaptiveAutomatonAddSubtypeEffect() {
		super(Duration.WhileOnBattlefield, Constants.Layer.TypeChangingEffects_4, Constants.SubLayer.NA, Constants.Outcome.Benefit);
		staticText = "{this} is the chosen type in addition to its other types";
	}

	public AdaptiveAutomatonAddSubtypeEffect(final AdaptiveAutomatonAddSubtypeEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (permanent != null) {
			String subtype = (String) game.getState().getValue(permanent.getId() + "_type");
			if (subtype != null && !permanent.getSubtype().contains(subtype)) {
				permanent.getSubtype().add(subtype);
			}
		}
		return true;
	}

	@Override
	public AdaptiveAutomatonAddSubtypeEffect copy() {
		return new AdaptiveAutomatonAddSubtypeEffect(this);
	}
}

class AdaptiveAutomatonBoostControlledEffect extends ContinuousEffectImpl<AdaptiveAutomatonBoostControlledEffect> {

	private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

	public AdaptiveAutomatonBoostControlledEffect() {
		super(Duration.WhileOnBattlefield, Constants.Layer.PTChangingEffects_7, Constants.SubLayer.ModifyPT_7c, Constants.Outcome.BoostCreature);
		staticText = "Other creatures you control of the chosen type get +1/+1";
	}

	public AdaptiveAutomatonBoostControlledEffect(final AdaptiveAutomatonBoostControlledEffect effect) {
		super(effect);
	}

	@Override
	public AdaptiveAutomatonBoostControlledEffect copy() {
		return new AdaptiveAutomatonBoostControlledEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (permanent != null) {
			String subtype = (String) game.getState().getValue(permanent.getId() + "_type");
			if (subtype != null) {
				for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
					if (!perm.getId().equals(source.getSourceId()) && perm.hasSubtype(subtype)) {
						perm.addPower(1);
						perm.addToughness(1);
					}
				}
			}
		}
		return true;
	}

}
