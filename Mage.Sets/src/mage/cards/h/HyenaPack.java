
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class HyenaPack extends CardImpl {

    public HyenaPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        
        this.subtype.add(SubType.HYENA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
    }

    private HyenaPack(final HyenaPack card) {
        super(card);
    }

    @Override
    public HyenaPack copy() {
        return new HyenaPack(this);
    }
}
