/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.mirrodinbesieged;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SpireSerpent extends CardImpl {

    private static final String abilityText1 = "Metalcraft - As long as you control three or more artifacts, {this} gets +2/+2";

    public SpireSerpent(UUID ownerId) {
        super(ownerId, 32, "Spire Serpent", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Serpent");
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        this.addAbility(DefenderAbility.getInstance());
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), MetalcraftCondition.getInstance(), abilityText1);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        Effect effect = new ConditionalAsThoughEffect(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                MetalcraftCondition.getInstance());
        effect.setText("and can attack as though it didn't have defender");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public SpireSerpent(final SpireSerpent card) {
        super(card);
    }

    @Override
    public SpireSerpent copy() {
        return new SpireSerpent(this);
    }
}
