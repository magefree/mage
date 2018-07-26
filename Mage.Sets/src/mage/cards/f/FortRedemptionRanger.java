package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;

import java.util.UUID;

/**
 *
 *
 * @author EikePeace
 */
public final class FortRedemptionRanger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control with a +1/+1 counter on it.");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public FortRedemptionRanger(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //Renown 1.
        this.addAbility(new RenownAbility(1));

        //Other creatures you control have Renown 1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new RenownAbility(1),Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent(),false)));

        //At the beginning of your end step, put a +1/+1 counter on each creature you control with a +1/+1 counter on it.
        this.addAbility( new BeginningOfEndStepTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), TargetController.YOU,false));

    }

    public FortRedemptionRanger(final FortRedemptionRanger card) {
        super(card);
    }

    @Override
    public FortRedemptionRanger copy() {
        return new FortRedemptionRanger(this);
    }
}
