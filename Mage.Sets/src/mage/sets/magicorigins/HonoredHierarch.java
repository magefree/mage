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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RenownCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.RenownAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class HonoredHierarch extends CardImpl {

    public HonoredHierarch(UUID ownerId) {
        super(ownerId, 182, "Honored Hierarch", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "ORI";
        this.subtype.add("Human");
        this.subtype.add("Druid");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Renown 1 <i>(When this creature deals combat damage to a player, if it isn't renowned put a +1/+1 counter on it and it becomes renowned.)<i>
        this.addAbility(new RenownAbility(1));

        // As long as Honored Hierarch is renowned, it has vigilance and "{T}: Add one mana of any color to your mana pool."
        Effect effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield),
                RenownCondition.getInstance(),
                "As long as {this} is renown, it has vigilance");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new AnyColorManaAbility(), Duration.WhileOnBattlefield),
                RenownCondition.getInstance(),
                "and \"{T}: Add one mana of any color to your mana pool.\"");
        ability.addEffect(effect);
        this.addAbility(ability);        
        
    }

    public HonoredHierarch(final HonoredHierarch card) {
        super(card);
    }

    @Override
    public HonoredHierarch copy() {
        return new HonoredHierarch(this);
    }
}
