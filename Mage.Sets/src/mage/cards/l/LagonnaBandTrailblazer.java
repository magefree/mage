
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class LagonnaBandTrailblazer extends CardImpl {

    public LagonnaBandTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Heroic — Whenever you cast a spell that targets Lagonna-Band Trailblazer, put a +1/+1 counter on Lagonna-Band Trailblzer.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private LagonnaBandTrailblazer(final LagonnaBandTrailblazer card) {
        super(card);
    }

    @Override
    public LagonnaBandTrailblazer copy() {
        return new LagonnaBandTrailblazer(this);
    }
}
