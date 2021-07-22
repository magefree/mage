package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Solarion extends CardImpl {

    public Solarion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sunburst
        this.addAbility(new SunburstAbility(this));

        // {tap}: Double the number of +1/+1 counters on Solarion.
        this.addAbility(new SimpleActivatedAbility(new DoubleCountersSourceEffect(CounterType.P1P1), new TapSourceCost()));
    }

    private Solarion(final Solarion card) {
        super(card);
    }

    @Override
    public Solarion copy() {
        return new Solarion(this);
    }
}
