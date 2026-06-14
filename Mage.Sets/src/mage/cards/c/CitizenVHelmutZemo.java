package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CitizenVHelmutZemo extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VILLAIN);

    public CitizenVHelmutZemo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you gain life, put a +1/+1 counter on each Villain you control.
        this.addAbility(new GainLifeControllerTriggeredAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ));
    }

    private CitizenVHelmutZemo(final CitizenVHelmutZemo card) {
        super(card);
    }

    @Override
    public CitizenVHelmutZemo copy() {
        return new CitizenVHelmutZemo(this);
    }
}
