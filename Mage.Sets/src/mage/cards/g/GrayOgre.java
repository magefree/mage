
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GrayOgre extends CardImpl {

    public GrayOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.OGRE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private GrayOgre(final GrayOgre card) {
        super(card);
    }

    @Override
    public GrayOgre copy() {
        return new GrayOgre(this);
    }
}
