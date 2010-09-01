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

package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.sets.Zendikar;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SpreadingSeas extends CardImpl<SpreadingSeas> {

	public SpreadingSeas(UUID ownerId) {
		super(ownerId, "Spreading Seas", new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
		this.expansionSetId = Zendikar.getInstance().getId();
		this.color.setBlue(true);
		this.subtype.add("Aura");

		TargetPermanent auraTarget = new TargetLandPermanent();
		this.getSpellAbility().addTarget(auraTarget);
		this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
		Ability ability = new EnchantAbility(Outcome.Detriment, auraTarget);
		this.addAbility(ability);
		this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardControllerEffect(1), false));
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpreadingSeasEffect()));

	}

	public SpreadingSeas(final SpreadingSeas card) {
		super(card);
	}

	@Override
	public SpreadingSeas copy() {
		return new SpreadingSeas(this);
	}

	@Override
	public String getArt() {
		return "123683_typ_reg_sty_010.jpg";
	}
}

class SpreadingSeasEffect extends ContinuousEffectImpl<SpreadingSeasEffect> {

	public SpreadingSeasEffect() {
		super(Duration.WhileOnBattlefield, Outcome.Detriment);
	}

	public SpreadingSeasEffect(final SpreadingSeasEffect effect) {
		super(effect);
	}

	@Override
	public SpreadingSeasEffect copy() {
		return new SpreadingSeasEffect(this);
	}

	@Override
	public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
		Permanent enchantment = game.getPermanent(source.getSourceId());
		if (enchantment.getAttachedTo() != null) {
			Permanent land = game.getPermanent(enchantment.getAttachedTo());
			if (land != null) {
				switch (layer) {
					case TypeChangingEffects_4:
						if (sublayer == SubLayer.NA) {
							land.getSubtype().clear();
							land.getSubtype().add("Island");
						}
						break;
					case AbilityAddingRemovingEffects_6:
						if (sublayer == SubLayer.NA) {
							land.getAbilities().clear();
							land.addAbility(new BlueManaAbility());
						}
						break;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return false;
	}

	@Override
	public boolean hasLayer(Layer layer) {
		return layer == Layer.AbilityAddingRemovingEffects_6 || layer == layer.TypeChangingEffects_4;
	}

	@Override
	public String getText(Ability source) {
		return "Enchanted land is an Island.";
	}
}