
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
public final class AxebaneStag extends CardImpl {

    public AxebaneStag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}");
        this.subtype.add(SubType.ELK);

        this.power = new MageInt(6);
        this.toughness = new MageInt(7);
    }

    private AxebaneStag(final AxebaneStag card) {
        super(card);
    }

    @Override
    public AxebaneStag copy() {
        return new AxebaneStag(this);
    }
}
