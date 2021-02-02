
package mage.cards.a;

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
public final class AlphaMyr extends CardImpl {

    public AlphaMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private AlphaMyr(final AlphaMyr card) {
        super(card);
    }

    @Override
    public AlphaMyr copy() {
        return new AlphaMyr(this);
    }
}
