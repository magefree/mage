
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class YokedOx extends CardImpl {

    public YokedOx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.OX);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
    }

    private YokedOx(final YokedOx card) {
        super(card);
    }

    @Override
    public YokedOx copy() {
        return new YokedOx(this);
    }
}
