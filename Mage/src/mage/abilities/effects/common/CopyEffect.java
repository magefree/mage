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

package mage.abilities.effects.common;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CopyEffect extends ContinuousEffectImpl<CopyEffect> {

    private MageObject target;
    
	public CopyEffect(MageObject target) {
		super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.NA, Outcome.BecomeCreature);
        this.target = target;
	}

	public CopyEffect(final CopyEffect effect) {
		super(effect);
        this.target = effect.target.copy();
	}

	@Override
	public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }

        permanent.setName(target.getName());
        permanent.getColor().setColor(target.getColor());
        permanent.getManaCost().clear();
        permanent.getManaCost().add(target.getManaCost());
        permanent.getCardType().clear();
        for (CardType type: target.getCardType()) {
            permanent.getCardType().add(type);
        }
        permanent.getSubtype().clear();
        for (String type: target.getSubtype()) {
            permanent.getSubtype().add(type);
        }
        permanent.getSupertype().clear();
        for (String type: target.getSupertype()) {
            permanent.getSupertype().add(type);
        }
        permanent.getAbilities().clear();
        for (Ability ability: target.getAbilities()) {
             permanent.addAbility(ability, game);
        }
        permanent.getPower().setValue(target.getPower().getValue());
        permanent.getToughness().setValue(target.getToughness().getValue());

        return true;
	}

	@Override
	public CopyEffect copy() {
		return new CopyEffect(this);
	}

}
