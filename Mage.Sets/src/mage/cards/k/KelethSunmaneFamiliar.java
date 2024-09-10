package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KelethSunmaneFamiliar extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("commander you control");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public KelethSunmaneFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a commander you control attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it"),
                false, filter, true
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private KelethSunmaneFamiliar(final KelethSunmaneFamiliar card) {
        super(card);
    }

    @Override
    public KelethSunmaneFamiliar copy() {
        return new KelethSunmaneFamiliar(this);
    }
}
