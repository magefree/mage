

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DregReaver extends CardImpl {

    public DregReaver (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private DregReaver(final DregReaver card) {
        super(card);
    }

    @Override
    public DregReaver copy() {
        return new DregReaver(this);
    }

}
