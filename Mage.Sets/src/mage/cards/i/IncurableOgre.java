

package mage.cards.i;

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
public final class IncurableOgre extends CardImpl {

    public IncurableOgre (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(1);
    }

    private IncurableOgre(final IncurableOgre card) {
        super(card);
    }

    @Override
    public IncurableOgre copy() {
        return new IncurableOgre(this);
    }
}
