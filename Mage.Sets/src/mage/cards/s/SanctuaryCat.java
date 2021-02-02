
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
public final class SanctuaryCat extends CardImpl {

    public SanctuaryCat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
    }

    private SanctuaryCat(final SanctuaryCat card) {
        super(card);
    }

    @Override
    public SanctuaryCat copy() {
        return new SanctuaryCat(this);
    }
}
