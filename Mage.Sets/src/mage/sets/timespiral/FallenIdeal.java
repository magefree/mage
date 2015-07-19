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
package mage.sets.timespiral;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class FallenIdeal extends CardImpl {

    public FallenIdeal(UUID ownerId) {
        super(ownerId, 109, "Fallen Ideal", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature has flying and "Sacrifice a creature: This creature gets +2/+1 until end of turn."
        Effect effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield);
        Ability gainedAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(gainedAbility);
        gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 1, Duration.EndOfTurn),
            new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent("a creature"), true)));
        effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature has \"Sacrifice a creature: This creature gets +2/+1 until end of turn.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // When Fallen Ideal is put into a graveyard from the battlefield, return Fallen Ideal to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));
    }

    public FallenIdeal(final FallenIdeal card) {
        super(card);
    }

    @Override
    public FallenIdeal copy() {
        return new FallenIdeal(this);
    }
}