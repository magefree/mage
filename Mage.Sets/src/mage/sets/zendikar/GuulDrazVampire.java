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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TenOrLessLife;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;

/**
 *
 * @author North, nantuko
 */
public class GuulDrazVampire extends CardImpl<GuulDrazVampire> {

    public GuulDrazVampire(UUID ownerId) {
        super(ownerId, 93, "Guul Draz Vampire", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Vampire");
        this.subtype.add("Rogue");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        ContinuousEffect effect = new GainAbilitySourceEffect(IntimidateAbility.getInstance(), Duration.WhileOnBattlefield);
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(effect, new TenOrLessLife(TenOrLessLife.CheckType.AN_OPPONENT), "As long as an opponent has 10 or less life, Guul Draz Vampire has intimidate"));
		ContinuousEffect effect2 = new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield);
		ability.addEffect(new ConditionalContinousEffect(effect2, new TenOrLessLife(TenOrLessLife.CheckType.AN_OPPONENT), "As long as an opponent has 10 or less life, Guul Draz Vampire gets +2/+1"));
		this.addAbility(ability);
    }

    public GuulDrazVampire(final GuulDrazVampire card) {
        super(card);
    }

    @Override
    public GuulDrazVampire copy() {
        return new GuulDrazVampire(this);
    }
}
