
package mage.cards.e;

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
public final class EnormousBaloth extends CardImpl {

    public EnormousBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
    }

    private EnormousBaloth(final EnormousBaloth card) {
        super(card);
    }

    @Override
    public EnormousBaloth copy() {
        return new EnormousBaloth(this);
    }
}
