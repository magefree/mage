package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Styxo
 */
public final class Aboroth extends CardImpl {

    public Aboroth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Cumulative upkeep-Put a -1/-1 counter on Aboroth.
        this.addAbility(new CumulativeUpkeepAbility(new PutCountersSourceCost(CounterType.M1M1.createInstance())));
    }

    private Aboroth(final Aboroth card) {
        super(card);
    }

    @Override
    public Aboroth copy() {
        return new Aboroth(this);
    }
}
