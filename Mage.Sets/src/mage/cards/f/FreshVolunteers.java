
package mage.cards.f;

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
public final class FreshVolunteers extends CardImpl {

    public FreshVolunteers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private FreshVolunteers(final FreshVolunteers card) {
        super(card);
    }

    @Override
    public FreshVolunteers copy() {
        return new FreshVolunteers(this);
    }
}
