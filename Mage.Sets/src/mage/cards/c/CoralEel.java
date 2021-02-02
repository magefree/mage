
package mage.cards.c;

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
public final class CoralEel extends CardImpl {

    public CoralEel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.FISH);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private CoralEel(final CoralEel card) {
        super(card);
    }

    @Override
    public CoralEel copy() {
        return new CoralEel(this);
    }
}
