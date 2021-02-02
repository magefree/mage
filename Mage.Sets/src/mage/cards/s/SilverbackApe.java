
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Quercitron
 */
public final class SilverbackApe extends CardImpl {

    public SilverbackApe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.APE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
    }

    private SilverbackApe(final SilverbackApe card) {
        super(card);
    }

    @Override
    public SilverbackApe copy() {
        return new SilverbackApe(this);
    }
}
