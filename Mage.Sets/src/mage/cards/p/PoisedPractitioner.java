package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.FlurryAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PoisedPractitioner extends CardImpl {

    public PoisedPractitioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flurry -- Whenever you cast your second spell each turn, put a +1/+1 counter on this creature. Scry 1.
        Ability ability = new FlurryAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new ScryEffect(1));
        this.addAbility(ability);
    }

    private PoisedPractitioner(final PoisedPractitioner card) {
        super(card);
    }

    @Override
    public PoisedPractitioner copy() {
        return new PoisedPractitioner(this);
    }
}
