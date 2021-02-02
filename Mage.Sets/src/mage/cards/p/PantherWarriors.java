
package mage.cards.p;

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
public final class PantherWarriors extends CardImpl {

    public PantherWarriors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(3);
    }

    private PantherWarriors(final PantherWarriors card) {
        super(card);
    }

    @Override
    public PantherWarriors copy() {
        return new PantherWarriors(this);
    }
}
