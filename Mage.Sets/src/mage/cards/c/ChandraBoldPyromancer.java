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
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public class ChandraBoldPyromancer extends CardImpl {

    public ChandraBoldPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Add {R}{R}. Chandra, Bold Pyromancer deals 2 damage to target player.
        Ability ability = new LoyaltyAbility(new BasicManaEffect(Mana.RedMana(2)), +1);
        ability.addEffect(new DamageTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // −3: Chandra, Bold Pyromancer deals 3 damage to target creature or planeswalker.
        ability = new LoyaltyAbility(new DamageTargetEffect(3), -3);
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

        // −7: Chandra, Bold Pyromancer deals 10 damage to target player and each creature and planeswalker they control.
        Effects effects1 = new Effects();
        effects1.add(new DamageTargetEffect(10));
        effects1.add(new DamageAllControlledTargetEffect(10, new FilterCreatureOrPlaneswalkerPermanent()).setText("and each creature and planeswalker they control"));
        LoyaltyAbility ability3 = new LoyaltyAbility(effects1, -7);
        ability3.addTarget(new TargetPlayer());
        this.addAbility(ability3);
    }

    public ChandraBoldPyromancer(final ChandraBoldPyromancer card) {
        super(card);
    }

    @Override
    public ChandraBoldPyromancer copy() {
        return new ChandraBoldPyromancer(this);
    }
}
