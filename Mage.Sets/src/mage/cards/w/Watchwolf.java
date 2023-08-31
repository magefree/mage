

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki, nantuko
 */
public final class Watchwolf extends CardImpl {

    public Watchwolf (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.subtype.add(SubType.WOLF);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private Watchwolf(final Watchwolf card) {
        super(card);
    }

    @Override
    public Watchwolf copy() {
        return new Watchwolf(this);
    }

}
