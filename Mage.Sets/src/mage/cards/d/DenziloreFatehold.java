package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.common.ScryOrSurveilTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DenziloreFatehold extends CardImpl {

    public DenziloreFatehold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you scry or surveil, put a +1/+1 counter on each creature you control.
        this.addAbility(new ScryOrSurveilTriggeredAbility(new AddCountersAllEffect(
            CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));
    }

    private DenziloreFatehold(final DenziloreFatehold card) {
        super(card);
    }

    @Override
    public DenziloreFatehold copy() {
        return new DenziloreFatehold(this);
    }
}
