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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public class EidolonOfCountlessBattles extends CardImpl<EidolonOfCountlessBattles> {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new SubtypePredicate("Aura")));
    }
    
    public EidolonOfCountlessBattles(UUID ownerId) {
        super(ownerId, 7, "Eidolon of Countless Battles", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Spirit");

        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Bestow {2}{W}{W}
        this.addAbility(new BestowAbility(this, "{2}{W}{W}"));
        // Eidolon of Countless Battles and enchanted creature get +1/+1 for each creature you control and +1/+1 for each Aura you control.        
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter, 1);
        Effect effect = new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield);
        effect.setText("{this} and enchanted creature get +1/+1 for each creature you control");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new BoostEnchantedEffect(amount, amount, Duration.WhileOnBattlefield);
        effect.setText("and +1/+1 for each Aura you control");
        ability.addEffect(effect);
        this.addAbility(ability); 
        
    }

    public EidolonOfCountlessBattles(final EidolonOfCountlessBattles card) {
        super(card);
    }

    @Override
    public EidolonOfCountlessBattles copy() {
        return new EidolonOfCountlessBattles(this);
    }
}
