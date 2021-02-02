
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
public final class ShuFootSoldiers extends CardImpl {

    public ShuFootSoldiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private ShuFootSoldiers(final ShuFootSoldiers card) {
        super(card);
    }

    @Override
    public ShuFootSoldiers copy() {
        return new ShuFootSoldiers(this);
    }
}
