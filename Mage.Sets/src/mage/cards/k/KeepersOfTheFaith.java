
package mage.cards.k;

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
public final class KeepersOfTheFaith extends CardImpl {

    public KeepersOfTheFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private KeepersOfTheFaith(final KeepersOfTheFaith card) {
        super(card);
    }

    @Override
    public KeepersOfTheFaith copy() {
        return new KeepersOfTheFaith(this);
    }
}
