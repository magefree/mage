package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoShintaiOfBoundlessVigor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.SHRINE, "Shrine");
    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SHRINE));
    private static final Hint hint = new ValueHint("Shrines you control", xValue);

    public GoShintaiOfBoundlessVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, you may pay {1}. When you do, put a +1/+1 counter on target Shrine for each Shrine you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(
                        CounterType.P1P1.createInstance(0), xValue
                ), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoWhenCostPaid(
                ability, new GenericManaCost(1), "Pay {1}?"
        ), TargetController.YOU, false).addHint(hint));
    }

    private GoShintaiOfBoundlessVigor(final GoShintaiOfBoundlessVigor card) {
        super(card);
    }

    @Override
    public GoShintaiOfBoundlessVigor copy() {
        return new GoShintaiOfBoundlessVigor(this);
    }
}
