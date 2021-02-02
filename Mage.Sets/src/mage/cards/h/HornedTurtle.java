

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class HornedTurtle extends CardImpl {

    public HornedTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
    }

    private HornedTurtle(final HornedTurtle card) {
        super(card);
    }

    @Override
    public HornedTurtle copy() {
        return new HornedTurtle(this);
    }

}
