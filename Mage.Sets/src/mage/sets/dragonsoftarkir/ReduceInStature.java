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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ReduceInStature extends CardImpl {

    public ReduceInStature(UUID ownerId) {
        super(ownerId, 72, "Reduce in Stature", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature has base power and toughness 0/2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessEnchantedEffect()));
        
    }

    public ReduceInStature(final ReduceInStature card) {
        super(card);
    }

    @Override
    public ReduceInStature copy() {
        return new ReduceInStature(this);
    }
}

class SetPowerToughnessEnchantedEffect extends ContinuousEffectImpl {

    public SetPowerToughnessEnchantedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "Enchanted creature has base power and toughness 0/2";
    }

    public SetPowerToughnessEnchantedEffect(final SetPowerToughnessEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public SetPowerToughnessEnchantedEffect copy() {
        return new SetPowerToughnessEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {            
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                enchanted.getPower().setValue(0);
                enchanted.getToughness().setValue(2);
            }
            return true;
        }
        return false;
    }

}
