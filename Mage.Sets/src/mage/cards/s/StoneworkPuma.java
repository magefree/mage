
package mage.cards.s;

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
public final class StoneworkPuma extends CardImpl {

    public StoneworkPuma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private StoneworkPuma(final StoneworkPuma card) {
        super(card);
    }

    @Override
    public StoneworkPuma copy() {
        return new StoneworkPuma(this);
    }
}
