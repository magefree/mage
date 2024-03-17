package mage.cards.c;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class CAMP extends CardImpl {

    public CAMP(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.subtype.add(SubType.FORTIFICATION);

        // Whenever fortified land is tapped for mana, put a +1/+1 counter on target creature you control. If that creature shares a color with the mana that land produced, create a Junk token.
        // Fortify {3}
    }

    private CAMP(final CAMP card) {
        super(card);
    }

    @Override
    public CAMP copy() {
        return new CAMP(this);
    }
}
