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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public class PheresBandWarchief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Other Centaur creatures you control");

    static {
        filter.add(new SubtypePredicate(SubType.CENTAUR));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public PheresBandWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add("Centaur");
        this.subtype.add("Warrior");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Other Centaur creatures you control get +1/+1 and have vigilance and trample.
        Effect effect = new BoostControlledEffect(1,1,Duration.WhileOnBattlefield, filter, true);
        effect.setText("Other Centaur creatures you control get +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter, true);
        effect.setText("and have vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true);
        effect.setText("and trample");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public PheresBandWarchief(final PheresBandWarchief card) {
        super(card);
    }

    @Override
    public PheresBandWarchief copy() {
        return new PheresBandWarchief(this);
    }
}
