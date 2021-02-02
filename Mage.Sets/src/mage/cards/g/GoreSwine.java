
package mage.cards.g;

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
public final class GoreSwine extends CardImpl {

    public GoreSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
    }

    private GoreSwine(final GoreSwine card) {
        super(card);
    }

    @Override
    public GoreSwine copy() {
        return new GoreSwine(this);
    }
}
