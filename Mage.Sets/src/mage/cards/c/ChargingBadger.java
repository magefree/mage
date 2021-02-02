
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ChargingBadger extends CardImpl {

    public ChargingBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.BADGER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private ChargingBadger(final ChargingBadger card) {
        super(card);
    }

    @Override
    public ChargingBadger copy() {
        return new ChargingBadger(this);
    }
}
