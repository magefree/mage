
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class AkroanSkyguard extends CardImpl {

    public AkroanSkyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Akroan Skyguard, put a +1/+1 counter on Akroan Skyguard.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true)));
    }

    private AkroanSkyguard(final AkroanSkyguard card) {
        super(card);
    }

    @Override
    public AkroanSkyguard copy() {
        return new AkroanSkyguard(this);
    }
}
