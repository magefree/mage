
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
public final class SavannahLions extends CardImpl {

    public SavannahLions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private SavannahLions(final SavannahLions card) {
        super(card);
    }

    @Override
    public SavannahLions copy() {
        return new SavannahLions(this);
    }
}
