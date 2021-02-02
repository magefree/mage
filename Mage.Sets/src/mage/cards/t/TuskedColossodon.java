
package mage.cards.t;

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
public final class TuskedColossodon extends CardImpl {

    public TuskedColossodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
    }

    private TuskedColossodon(final TuskedColossodon card) {
        super(card);
    }

    @Override
    public TuskedColossodon copy() {
        return new TuskedColossodon(this);
    }
}
