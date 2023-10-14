

package mage.cards.b;

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


public final class BaneAlleyBlackguard extends CardImpl {

    public BaneAlleyBlackguard (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
    }

    private BaneAlleyBlackguard(final BaneAlleyBlackguard card) {
        super(card);
    }

    @Override
    public BaneAlleyBlackguard copy() {
        return new BaneAlleyBlackguard(this);
    }

}
