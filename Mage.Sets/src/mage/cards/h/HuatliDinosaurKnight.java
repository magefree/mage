package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL;

/**
 * @author TheElk801
 */
public final class HuatliDinosaurKnight extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DINOSAUR);
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.DINOSAUR, "Dinosaurs");

    public HuatliDinosaurKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUATLI);

        this.setStartingLoyalty(4);

        // +2: Put two +1/+1 counters on up to one target Dinosaur you control.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
                .setText("Put two +1/+1 counters on up to one target Dinosaur you control."), 2
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // -3: Target Dinosaur you control deals damage equal to its power to target creature you don't control.
        ability = new LoyaltyAbility(new DamageWithPowerFromOneToAnotherTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetPermanent(FILTER_CREATURE_YOU_DONT_CONTROL));
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
