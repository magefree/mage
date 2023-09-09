

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Cobblebrute extends CardImpl {

    public Cobblebrute (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(2);
    }

    private Cobblebrute(final Cobblebrute card) {
        super(card);
    }

    @Override
    public Cobblebrute copy() {
        return new Cobblebrute(this);
    }
}
