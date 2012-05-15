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
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OneControlledCreatureCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;

import java.util.UUID;

/**
 * @author noxx
 */
public class HomicidalSeclusion extends CardImpl<HomicidalSeclusion> {

    private static final String rule = "As long as you control exactly one creature, that creature gets +3/+1 and lifelink";

    public HomicidalSeclusion(UUID ownerId) {
        super(ownerId, 108, "Homicidal Seclusion", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");
        this.expansionSetCode = "AVR";

        this.color.setBlack(true);

        // As long as you control exactly one creature, that creature gets +3/+1 and has lifelink.
        ContinuousEffect boostEffect = new BoostControlledEffect(3, 1, Constants.Duration.WhileOnBattlefield);
        Effect effect = new ConditionalContinousEffect(boostEffect, new OneControlledCreatureCondition(), rule);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect));

        ContinuousEffect lifelinkEffect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Constants.Duration.WhileOnBattlefield);
        effect = new ConditionalContinousEffect(lifelinkEffect, new OneControlledCreatureCondition(), null);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect));
    }

    public HomicidalSeclusion(final HomicidalSeclusion card) {
        super(card);
    }

    @Override
    public HomicidalSeclusion copy() {
        return new HomicidalSeclusion(this);
    }
}
