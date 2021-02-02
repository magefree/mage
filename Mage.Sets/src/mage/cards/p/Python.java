
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
public final class Python extends CardImpl {

    public Python(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private Python(final Python card) {
        super(card);
    }

    @Override
    public Python copy() {
        return new Python(this);
    }
}
