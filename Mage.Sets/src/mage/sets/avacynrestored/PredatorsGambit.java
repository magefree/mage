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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OneControlledCreatureCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public class PredatorsGambit extends CardImpl<PredatorsGambit> {

    private static final String rule = "Enchanted creature has intimidate as long as its controller controls no other creatures";
    
    public PredatorsGambit(UUID ownerId) {
        super(ownerId, 117, "Predator's Gambit", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Aura");

        this.color.setBlack(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.BoostCreature));

        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 1, Constants.Duration.WhileOnBattlefield)));

        // Enchanted creature has intimidate as long as its controller controls no other creatures.
        ContinuousEffect effect = new GainAbilityAttachedEffect(IntimidateAbility.getInstance(), Constants.AttachmentType.AURA);
        ConditionalContinousEffect intimidate = new ConditionalContinousEffect(effect, new OneControlledCreatureCondition(), rule);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, intimidate));

    }

    public PredatorsGambit(final PredatorsGambit card) {
        super(card);
    }

    @Override
    public PredatorsGambit copy() {
        return new PredatorsGambit(this);
    }
}
