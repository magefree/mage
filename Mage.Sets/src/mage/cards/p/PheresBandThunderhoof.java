
package mage.cards.p;

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
public final class PheresBandThunderhoof extends CardImpl {

    public PheresBandThunderhoof(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Heroic - Whenever you cast a spell that targets Pheres-Band Thunderhood, put two +1/+1 counters on Pheres-Band Thunderhoof.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2), true)));
    }

    private PheresBandThunderhoof(final PheresBandThunderhoof card) {
        super(card);
    }

    @Override
    public PheresBandThunderhoof copy() {
        return new PheresBandThunderhoof(this);
    }
}
