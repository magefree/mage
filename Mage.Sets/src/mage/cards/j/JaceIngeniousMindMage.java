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
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class JaceIngeniousMindMage extends CardImpl {

    public JaceIngeniousMindMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Jace");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Draw a card.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1));

        // +1: Untap all creatures you control.
        this.addAbility(new LoyaltyAbility(new UntapAllControllerEffect(FILTER_PERMANENT_CREATURES), 1));

        // -9: Gain control of up to three target creatures.
        Ability ability = new LoyaltyAbility(new GainControlTargetEffect(Duration.Custom), -9);
        ability.addTarget(new TargetCreaturePermanent(0, 3));
        this.addAbility(ability);
    }

    public JaceIngeniousMindMage(final JaceIngeniousMindMage card) {
        super(card);
    }

    @Override
    public JaceIngeniousMindMage copy() {
        return new JaceIngeniousMindMage(this);
    }
}
