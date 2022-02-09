package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuatliDinosaurKnight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dinosaur you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Dinosaurs");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(SubType.DINOSAUR.getPredicate());
    }

    public HuatliDinosaurKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUATLI);

        this.setStartingLoyalty(4);

        // +2: Put two +1/+1 counters on up to one target Dinosaur you control.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
                .setText("Put two +1/+1 counters on up to one target Dinosaur you control."), 2
        );
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);

        // -3: Target Dinosaur you control deals damage equal to its power to target creature you don't control.
        ability = new LoyaltyAbility(new DamageWithPowerFromOneToAnotherTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // -7: Dinosaurs you control get +4/+4 until end of turn.
        this.addAbility(new LoyaltyAbility(new BoostControlledEffect(4, 4, Duration.EndOfTurn, filter2), -7));
    }

    private HuatliDinosaurKnight(final HuatliDinosaurKnight card) {
        super(card);
    }

    @Override
    public HuatliDinosaurKnight copy() {
        return new HuatliDinosaurKnight(this);
    }
}
