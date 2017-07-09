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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.MasterOfWavesElementalToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class MasterOfWaves extends CardImpl {

    private static final FilterCreaturePermanent filterBoost = new FilterCreaturePermanent("Elemental creatures");
    static {
        filterBoost.add(new SubtypePredicate(SubType.ELEMENTAL));
    }

    public MasterOfWaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        // Elemental creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterBoost, false)));
        // When Master of Waves enters the battlefield, create a number of 1/0 blue Elemental creature tokens equal to your devotion to blue.
        // <i>(Each {U} in the mana costs of permanents you control counts toward your devotion to blue.)</i>
        Effect effect = new CreateTokenEffect(new MasterOfWavesElementalToken(), new DevotionCount(ColoredManaSymbol.U));
        effect.setText("create a number of 1/0 blue Elemental creature tokens equal to your devotion to blue. <i>(Each {U} in the mana costs of permanents you control counts toward your devotion to blue.)</i>");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

    }

    public MasterOfWaves(final MasterOfWaves card) {
        super(card);
    }

    @Override
    public MasterOfWaves copy() {
        return new MasterOfWaves(this);
    }
}
