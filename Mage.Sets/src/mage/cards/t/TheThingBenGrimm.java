package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.abilities.common.OneOrMoreDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheThingBenGrimm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HERO, "Heroes");

    public TheThingBenGrimm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever one or more Heroes you control deal damage to a player, put two +1/+1 counters on The Thing.
        this.addAbility(new OneOrMoreDamagePlayerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            filter, false, true
        ));
    }

    private TheThingBenGrimm(final TheThingBenGrimm card) {
        super(card);
    }

    @Override
    public TheThingBenGrimm copy() {
        return new TheThingBenGrimm(this);
    }
}
