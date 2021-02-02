
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ColossodonYearling extends CardImpl {

    public ColossodonYearling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
    }

    private ColossodonYearling(final ColossodonYearling card) {
        super(card);
    }

    @Override
    public ColossodonYearling copy() {
        return new ColossodonYearling(this);
    }
}
