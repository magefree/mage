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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.common.DamageWithPowerTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class HuatliDinosaurKnight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dinosaur you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature you don't control");
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent("Dinosaurs you control");

    static {
        filter.add(new SubtypePredicate(SubType.DINOSAUR));
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter3.add(new SubtypePredicate(SubType.DINOSAUR));
        filter3.add(new ControllerPredicate(TargetController.YOU));
    }

    public HuatliDinosaurKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUATLI);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +2: Put two +1/+1 counters on up to one target Dinosaur you control.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), 2);
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);

        // -3: Target Dinosaur you control deals damage equal to its power to target creature you don't control.
        ability = new LoyaltyAbility(new DamageWithPowerTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);

        // -7: Dinosaurs you control get +4/+4 until end of turn.
        this.addAbility(new LoyaltyAbility(new BoostControlledEffect(4, 4, Duration.EndOfTurn, filter3), -7));
    }

    public HuatliDinosaurKnight(final HuatliDinosaurKnight card) {
        super(card);
    }

    @Override
    public HuatliDinosaurKnight copy() {
        return new HuatliDinosaurKnight(this);
    }
}
