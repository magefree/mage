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
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class HundredHandedOne extends CardImpl<HundredHandedOne> {

    public HundredHandedOne(UUID ownerId) {
        super(ownerId, 20, "Hundred-Handed One", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "THS";
        this.subtype.add("Giant");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {3}{W}{W}{W}: Monstrosity 3. <i>(If this creature isn't monstrous, put three +1/+1 counters on it and it becomes monstrous.)</i>
        this.addAbility(new MonstrosityAbility("{3}{W}{W}{W}", 3));

        // As long as Hundred-Handed One is monstrous, it has reach and can block an additional ninety-nine creatures each combat.
        ConditionalContinousEffect effect1 = new ConditionalContinousEffect(
                new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.WhileOnBattlefield), 
                MonstrousCondition.getInstance(), 
                "As long as Hundred-Handed One is monstrous, it has reach");
        ConditionalContinousEffect effect2 = new ConditionalContinousEffect(
                new CanBlockAdditionalCreatureEffect(99), 
                MonstrousCondition.getInstance(), 
                "and can block an additional ninety-nine creatures each combat");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(effect2);
        this.addAbility(ability);

    }

    public HundredHandedOne(final HundredHandedOne card) {
        super(card);
    }

    @Override
    public HundredHandedOne copy() {
        return new HundredHandedOne(this);
    }
}
