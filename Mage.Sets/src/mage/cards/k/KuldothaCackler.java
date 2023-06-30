package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KuldothaCackler extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("permanents you control with oil counters on them");

    static {
        filter.add(CounterType.OIL.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Permanents you control with oil counters on them", xValue);

    public KuldothaCackler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HYENA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Kuldotha Cackler attacks, it gets +X/+0 until end of turn, where X is the number of permanents you control with oil counters on them.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn, true, "it"
        )).addHint(hint));
    }

    private KuldothaCackler(final KuldothaCackler card) {
        super(card);
    }

    @Override
    public KuldothaCackler copy() {
        return new KuldothaCackler(this);
    }
}
