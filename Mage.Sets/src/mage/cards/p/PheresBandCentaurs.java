
package mage.cards.p;

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
public final class PheresBandCentaurs extends CardImpl {

    public PheresBandCentaurs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(7);
    }

    private PheresBandCentaurs(final PheresBandCentaurs card) {
        super(card);
    }

    @Override
    public PheresBandCentaurs copy() {
        return new PheresBandCentaurs(this);
    }
}
