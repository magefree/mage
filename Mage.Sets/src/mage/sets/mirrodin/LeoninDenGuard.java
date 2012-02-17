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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;

/**
 *
 * @author Loki
 */
public class LeoninDenGuard extends CardImpl<LeoninDenGuard> {

    private static final String rule1 = "As long as {this} is equipped, it gets +1/+1";
    private static final String rule2 = "As long as {this} is equipped, it has vigilance";

    public LeoninDenGuard(UUID ownerId) {
        super(ownerId, 9, "Leonin Den-Guard", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Cat");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As long as Leonin Den-Guard is equipped, it gets +1/+1 and has vigilance.        
        ConditionalContinousEffect effect1 = new ConditionalContinousEffect(new BoostSourceEffect(1, 1, Constants.Duration.WhileOnBattlefield), EquippedCondition.getInstance(), rule1);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect1));
        ConditionalContinousEffect effect2 = new ConditionalContinousEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance()), EquippedCondition.getInstance(), rule2);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect2));

    }

    public LeoninDenGuard(final LeoninDenGuard card) {
        super(card);
    }

    @Override
    public LeoninDenGuard copy() {
        return new LeoninDenGuard(this);
    }
}
