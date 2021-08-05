package mage.cards.g;

import mage.MageInt;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PackTacticsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnollHunter extends CardImpl {

    public GnollHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.GNOLL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Pact tactics — Whenever Gnoll Hunter attacks, if you attacked with creatures with total power 6 or greater this combat, put a +1/+1 counter on Gnoll Hunter.
        this.addAbility(new PackTacticsAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private GnollHunter(final GnollHunter card) {
        super(card);
    }

    @Override
    public GnollHunter copy() {
        return new GnollHunter(this);
    }
}
