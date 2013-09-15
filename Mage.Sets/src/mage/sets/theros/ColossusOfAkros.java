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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.CanAttackAsThoughtItDidntHaveDefenderEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class ColossusOfAkros extends CardImpl<ColossusOfAkros> {

    public ColossusOfAkros(UUID ownerId) {
        super(ownerId, 214, "Colossus of Akros", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");
        this.expansionSetCode = "THS";
        this.subtype.add("Golem");

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // indestructible.
        this.addAbility(IndestructibleAbility.getInstance());
        // {10}: Monstrosity 10.
        this.addAbility(new MonstrosityAbility("{10}", 10));
        // As long as Colossus of Akros is monstrous, it has trample and can attack as though it didn't have defender.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                MonstrousCondition.getInstance(),
                "As long as Colossus of Akros is monstrous, it has trample"));
        Effect effect = new ConditionalAsThoughEffect(new CanAttackAsThoughtItDidntHaveDefenderEffect(Duration.WhileOnBattlefield),
                MonstrousCondition.getInstance(), false);
        effect.setText("and can attack as though it didn't have defender");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public ColossusOfAkros(final ColossusOfAkros card) {
        super(card);
    }

    @Override
    public ColossusOfAkros copy() {
        return new ColossusOfAkros(this);
    }
}
