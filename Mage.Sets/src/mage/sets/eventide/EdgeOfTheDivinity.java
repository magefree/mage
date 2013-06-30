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
package mage.sets.eventide;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCreatureColorCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class EdgeOfTheDivinity extends CardImpl<EdgeOfTheDivinity> {

    public EdgeOfTheDivinity(UUID ownerId) {
        super(ownerId, 87, "Edge of the Divinity", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{W/B}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Aura");

        this.color.setBlack(true);
        this.color.setWhite(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // As long as enchanted creature is white, it gets +1/+2.
        SimpleStaticAbility whiteAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(new BoostEnchantedEffect(1, 2), new EnchantedCreatureColorCondition(ObjectColor.WHITE), "As long as enchanted creature is white, it gets +1/+2"));
        this.addAbility(whiteAbility);
        // As long as enchanted creature is black, it gets +2/+1.
        SimpleStaticAbility blackAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(new BoostEnchantedEffect(2, 1), new EnchantedCreatureColorCondition(ObjectColor.BLACK), "As long as enchanted creature is black, it gets +2/+1"));
        this.addAbility(blackAbility);
    }

    public EdgeOfTheDivinity(final EdgeOfTheDivinity card) {
        super(card);
    }

    @Override
    public EdgeOfTheDivinity copy() {
        return new EdgeOfTheDivinity(this);
    }
}
