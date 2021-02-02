
package mage.cards.s;

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
public final class SwordwiseCentaur extends CardImpl {

    public SwordwiseCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private SwordwiseCentaur(final SwordwiseCentaur card) {
        super(card);
    }

    @Override
    public SwordwiseCentaur copy() {
        return new SwordwiseCentaur(this);
    }
}
