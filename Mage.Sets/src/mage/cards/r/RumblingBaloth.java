
package mage.cards.r;

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
public final class RumblingBaloth extends CardImpl {

    public RumblingBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private RumblingBaloth(final RumblingBaloth card) {
        super(card);
    }

    @Override
    public RumblingBaloth copy() {
        return new RumblingBaloth(this);
    }
}
