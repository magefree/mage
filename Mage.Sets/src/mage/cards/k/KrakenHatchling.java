

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class KrakenHatchling extends CardImpl {

    public KrakenHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

    }

    private KrakenHatchling(final KrakenHatchling card) {
        super(card);
    }

    @Override
    public KrakenHatchling copy() {
        return new KrakenHatchling(this);
    }

}
