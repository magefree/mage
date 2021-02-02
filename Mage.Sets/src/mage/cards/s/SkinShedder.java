
package mage.cards.s;

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
public final class SkinShedder extends CardImpl {

    public SkinShedder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.color.setRed(true);

        this.nightCard = true;

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
    }

    private SkinShedder(final SkinShedder card) {
        super(card);
    }

    @Override
    public SkinShedder copy() {
        return new SkinShedder(this);
    }

}
