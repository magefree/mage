

package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class BloodiedGhost extends CardImpl {

    public BloodiedGhost (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Bloodied Ghost enters the battlefield with a -1/-1 counter on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.M1M1.createInstance()));
    }

    private BloodiedGhost(final BloodiedGhost card) {
        super(card);
    }

    @Override
    public BloodiedGhost copy() {
        return new BloodiedGhost(this);
    }

}
