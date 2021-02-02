
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class HeadwaterSentries extends CardImpl {

    public HeadwaterSentries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
    }

    private HeadwaterSentries(final HeadwaterSentries card) {
        super(card);
    }

    @Override
    public HeadwaterSentries copy() {
        return new HeadwaterSentries(this);
    }
}
