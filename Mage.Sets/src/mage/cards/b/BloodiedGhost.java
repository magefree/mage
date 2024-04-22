

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class BloodiedGhost extends CardImpl {

    public BloodiedGhost (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W/B}{W/B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Bloodied Ghost enters the battlefield with a -1/-1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance()),
            "with a -1/-1 counter on it."));
    }

    private BloodiedGhost(final BloodiedGhost card) {
        super(card);
    }

    @Override
    public BloodiedGhost copy() {
        return new BloodiedGhost(this);
    }

}
