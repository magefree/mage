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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.effects.common.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.continious.SetCardColorTargetEffect;
import mage.cards.CardImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Incite extends CardImpl<Incite> {

	public Incite(UUID ownerId) {
		super(ownerId, 145, "Incite", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
		this.expansionSetCode = "M11";
		this.color.setRed(true);
                // Target creature becomes red until end of turn and attacks this turn if able.
                this.getSpellAbility().addTarget(new TargetCreaturePermanent());
//		this.getSpellAbility().addEffect(new InciteEffect());
                this.getSpellAbility().addEffect(new SetCardColorTargetEffect(ObjectColor.RED, Constants.Duration.EndOfTurn, "Target creature becomes red until end of turn"));
		this.getSpellAbility().addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn));
	}

	public Incite(final Incite card) {
		super(card);
	}

	@Override
	public Incite copy() {
		return new Incite(this);
	}
}

//class InciteEffect extends ContinuousEffectImpl<InciteEffect> {
//
//	public InciteEffect() {
//		super(Duration.EndOfTurn, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Detriment);
//		staticText = "Target creature becomes red until end of turn";
//	}
//
//	public InciteEffect(final InciteEffect effect) {
//		super(effect);
//	}
//
//	@Override
//	public InciteEffect copy() {
//		return new InciteEffect(this);
//	}
//
//	@Override
//	public boolean apply(Game game, Ability source) {
//		Permanent permanent = game.getPermanent(source.getFirstTarget());
//		if (permanent != null) {
//			permanent.getColor().setRed(true);
//			permanent.getColor().setWhite(false);
//			permanent.getColor().setGreen(false);
//			permanent.getColor().setBlue(false);
//			permanent.getColor().setBlack(false);
//			return true;
//		}
//		return false;
//	}
//
//}
