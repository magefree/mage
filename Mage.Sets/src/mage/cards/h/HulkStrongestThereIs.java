package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.effects.common.counter.DoubleCounterOnEachPermanentEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HulkStrongestThereIs extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Gamma creature you control");

    static {
        filter.add(SubType.GAMMA.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public HulkStrongestThereIs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Hulk enters with a +1/+1 counter on him.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance()));

        // At the beginning of your upkeep, double the number of +1/+1 counters on each Gamma creature you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            new DoubleCounterOnEachPermanentEffect(CounterType.P1P1, filter)
        ));
    }

    private HulkStrongestThereIs(final HulkStrongestThereIs card) {
        super(card);
    }

    @Override
    public HulkStrongestThereIs copy() {
        return new HulkStrongestThereIs(this);
    }
}
