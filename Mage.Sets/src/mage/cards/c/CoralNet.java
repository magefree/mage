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
package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class CoralNet extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green or white creature");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.GREEN)));
    }
    
    static private final String rule = "Enchanted creature has \"At the beginning of your upkeep, sacrifice this creature unless you discard a card.\"";

    public CoralNet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.subtype.add("Aura");

        // Enchant green or white creature
        TargetPermanent auraTarget = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        
        // Enchanted creature has "At the beginning of your upkeep, sacrifice this creature unless you discard a card."
        Ability abilityToGain = new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new DiscardCardCost()), TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new GainAbilityAttachedEffect(abilityToGain, AttachmentType.AURA, Duration.WhileOnBattlefield, rule)));
    }

    public CoralNet(final CoralNet card) {
        super(card);
    }

    @Override
    public CoralNet copy() {
        return new CoralNet(this);
    }
}
