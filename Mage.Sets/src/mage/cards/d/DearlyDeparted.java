package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class DearlyDeparted extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.HUMAN, "Human creature");

    public DearlyDeparted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());

        // As long as Dearly Departed is in your graveyard, each Human creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new EntersWithCountersControlledEffect(
                filter, CounterType.P1P1.createInstance(), false
        ).setText("as long as {this} is in your graveyard, each Human creature you control enters with an additional +1/+1 counter on it")));
    }

    private DearlyDeparted(final DearlyDeparted card) {
        super(card);
    }

    @Override
    public DearlyDeparted copy() {
        return new DearlyDeparted(this);
    }
}
