
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LatchSeeker extends CardImpl {

    public LatchSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Latch Seeker can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private LatchSeeker(final LatchSeeker card) {
        super(card);
    }

    @Override
    public LatchSeeker copy() {
        return new LatchSeeker(this);
    }
}
