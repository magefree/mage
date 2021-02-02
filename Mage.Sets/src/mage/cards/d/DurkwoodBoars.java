
package mage.cards.d;

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
public final class DurkwoodBoars extends CardImpl {

    public DurkwoodBoars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private DurkwoodBoars(final DurkwoodBoars card) {
        super(card);
    }

    @Override
    public DurkwoodBoars copy() {
        return new DurkwoodBoars(this);
    }
}
