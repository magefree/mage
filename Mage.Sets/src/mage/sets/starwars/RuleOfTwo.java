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
package mage.sets.starwars;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class RuleOfTwo extends CardImpl {

    public RuleOfTwo(UUID ownerId) {
        super(ownerId, 85, "Rule of two", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "SWS";

        // As long as you control exatly two creatures, those creatures get +2/+0 and have deathtouch and lifelink.
        ContinuousEffect boostEffect = new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield);
        Effect effect = new ConditionalContinuousEffect(boostEffect, new CreatureCountCondition(2, TargetController.YOU),
                "As long as you control exactly two creatures, those creatures get +2/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ContinuousEffect deathtouchEffect = new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent());
        effect = new ConditionalContinuousEffect(deathtouchEffect, new CreatureCountCondition(2, TargetController.YOU),
                "and have deathtouch");
        ability.addEffect(effect);
        ContinuousEffect lifelinkEffect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent());
        effect = new ConditionalContinuousEffect(lifelinkEffect, new CreatureCountCondition(2, TargetController.YOU),
                "and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public RuleOfTwo(final RuleOfTwo card) {
        super(card);
    }

    @Override
    public RuleOfTwo copy() {
        return new RuleOfTwo(this);
    }
}
