
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
public final class ShuEliteInfantry extends CardImpl {

    public ShuEliteInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private ShuEliteInfantry(final ShuEliteInfantry card) {
        super(card);
    }

    @Override
    public ShuEliteInfantry copy() {
        return new ShuEliteInfantry(this);
    }
}
