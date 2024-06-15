
package mage.cards.c;

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
public final class CogworkLibrarian extends CardImpl {

    public CogworkLibrarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private CogworkLibrarian(final CogworkLibrarian card) {
        super(card);
    }

    @Override
    public CogworkLibrarian copy() {
        return new CogworkLibrarian(this);
    }
}
