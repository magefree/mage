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

package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RiseFromTheGrave extends CardImpl<RiseFromTheGrave> {

	public RiseFromTheGrave(UUID ownerId) {
		super(ownerId, 109, "Rise from the Grave", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{B}");
		this.expansionSetCode = "M10";
		this.color.setBlack(true);
		this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
		this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
		this.getSpellAbility().addEffect(new RiseFromTheGraveEffect());
	}

	public RiseFromTheGrave(final RiseFromTheGrave card) {
		super(card);
	}

	@Override
	public RiseFromTheGrave copy() {
		return new RiseFromTheGrave(this);
	}
}

class RiseFromTheGraveEffect extends ContinuousEffectImpl<RiseFromTheGraveEffect> {

	public RiseFromTheGraveEffect() {
		super(Duration.Custom, Outcome.Neutral);
		staticText = "That creature is a black Zombie in addition to its other colors and types";
	}

	public RiseFromTheGraveEffect(final RiseFromTheGraveEffect effect) {
		super(effect);
	}

	@Override
	public RiseFromTheGraveEffect copy() {
		return new RiseFromTheGraveEffect(this);
	}

	@Override
	public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
		Permanent creature = game.getPermanent(source.getFirstTarget());
		if (creature != null) {
			switch (layer) {
				case TypeChangingEffects_4:
					if (sublayer == SubLayer.NA) {
						creature.getSubtype().add("Zombie");
					}
					break;
				case ColorChangingEffects_5:
					if (sublayer == SubLayer.NA) {
						creature.getColor().setBlack(true);
					}
					break;
			}
			return true;
		} else {
            this.used = true;
        }
		return false;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return false;
	}

	@Override
	public boolean hasLayer(Layer layer) {
		return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
	}

}