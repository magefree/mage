
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
public final class SouthernElephant extends CardImpl {

    public SouthernElephant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELEPHANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
    }

    private SouthernElephant(final SouthernElephant card) {
        super(card);
    }

    @Override
    public SouthernElephant copy() {
        return new SouthernElephant(this);
    }
}
