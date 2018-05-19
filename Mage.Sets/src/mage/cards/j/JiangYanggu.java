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
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.MowuToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class JiangYanggu extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature name Mowu");

    static {
        filter.add(new NamePredicate("Mowu"));
    }

    public JiangYanggu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.YANGGU);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Target creature gets +2/+2 until end of turn.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -1: If you don't control a creature named Mowu, creature a legendary 3/3 green Hound creature token named Mowu.
        this.addAbility(new LoyaltyAbility(new ConditionalOneShotEffect(
                new CreateTokenEffect(new MowuToken()),
                new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
                "If you don't control a creature named Mowu, "
                + "creature a legendary 3/3 green Hound creature token named Mowu."
        ), -1));

        // -5: Until end of turn, target creature gains trample and gets +X/+X, where X is the number of lands you control.
        DynamicValue controlledLands = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);
        ability = new LoyaltyAbility(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(),
                Duration.EndOfTurn,
                "Until end of turn, target creature gains trample"
        ), -5);
        ability.addEffect(
                new BoostTargetEffect(controlledLands, controlledLands, Duration.EndOfTurn, true)
                        .setText("and gets +X/+X, where X is the number of lands you control")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public JiangYanggu(final JiangYanggu card) {
        super(card);
    }

    @Override
    public JiangYanggu copy() {
        return new JiangYanggu(this);
    }
}
