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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Plopman
 */
public class RakshaGoldenCub extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Cat creatures you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate("Cat"));
    }
    
    public RakshaGoldenCub(UUID ownerId) {
        super(ownerId, 12, "Raksha Golden Cub", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.expansionSetCode = "5DN";
        this.supertype.add("Legendary");
        this.subtype.add("Cat");
        this.subtype.add("Soldier");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // As long as Raksha Golden Cub is equipped, Cat creatures you control get +2/+2 and have double strike.
        Effect effect1 = new ConditionalContinuousEffect(
                new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, false),
                EquippedCondition.getInstance(),
                "As long as {this} is equipped, Cat creatures you control get +2/+2");
        
        Effect effect2 = new ConditionalContinuousEffect(
                new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, false),
                EquippedCondition.getInstance(),
                "As long as {this} is equipped, Cat creatures you control have double strike");
        effect2.setText("and have double strike");
        
        
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(effect2);
        
        this.addAbility(ability);
    }

    public RakshaGoldenCub(final RakshaGoldenCub card) {
        super(card);
    }

    @Override
    public RakshaGoldenCub copy() {
        return new RakshaGoldenCub(this);
    }
}
