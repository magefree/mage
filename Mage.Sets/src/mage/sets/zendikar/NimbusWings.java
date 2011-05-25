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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class NimbusWings extends CardImpl<NimbusWings> {

    public NimbusWings (UUID ownerId) {
        super(ownerId, 28, "Nimbus Wings", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Aura");
        this.color.setWhite(true);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NimbusWingsEffect()));
    }

    public NimbusWings (final NimbusWings card) {
        super(card);
    }

    @Override
    public NimbusWings copy() {
        return new NimbusWings(this);
    }
}

class NimbusWingsEffect extends ContinuousEffectImpl<NimbusWingsEffect> {

    public NimbusWingsEffect() {
        super(Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
    }

    public NimbusWingsEffect(final NimbusWingsEffect effect) {
        super(effect);
    }

    @Override
	public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
		Permanent enchantment = game.getPermanent(source.getSourceId());
		if (enchantment != null && enchantment.getAttachedTo() != null) {
			Permanent creature = game.getPermanent(enchantment.getAttachedTo());
			if (creature != null) {
				switch (layer) {
					case PTChangingEffects_7:
						if (sublayer == Constants.SubLayer.ModifyPT_7c) {
							creature.addPower(1);
							creature.addToughness(2);
						}
						break;
					case AbilityAddingRemovingEffects_6:
						if (sublayer == Constants.SubLayer.NA) {
							creature.addAbility(FlyingAbility.getInstance());
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
	public boolean hasLayer(Constants.Layer layer) {
		return layer == Constants.Layer.AbilityAddingRemovingEffects_6 || layer == layer.PTChangingEffects_7;
	}

    @Override
    public NimbusWingsEffect copy() {
        return new NimbusWingsEffect(this);
    }

    @Override
	public String getText(Ability source) {
		return "Enchanted creature gets +1/+2 and has flying";
	}
}
