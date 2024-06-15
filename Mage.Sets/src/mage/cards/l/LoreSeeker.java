
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author tiera3 - based on PrizefighterConstruct
 * note - draftmatters ability not implemented
 */
public final class LoreSeeker extends CardImpl {

    public LoreSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private LoreSeeker(final LoreSeeker card) {
        super(card);
    }

    @Override
    public LoreSeeker copy() {
        return new LoreSeeker(this);
    }
}
