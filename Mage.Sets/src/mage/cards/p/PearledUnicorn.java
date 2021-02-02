
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
public final class PearledUnicorn extends CardImpl {

    public PearledUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.UNICORN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private PearledUnicorn(final PearledUnicorn card) {
        super(card);
    }

    @Override
    public PearledUnicorn copy() {
        return new PearledUnicorn(this);
    }
}
