
package mage.cards.t;

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
public final class TasseledDromedary extends CardImpl {

    public TasseledDromedary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
    }

    private TasseledDromedary(final TasseledDromedary card) {
        super(card);
    }

    @Override
    public TasseledDromedary copy() {
        return new TasseledDromedary(this);
    }
}
