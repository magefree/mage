
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class FrenziedRaptor extends CardImpl {

    public FrenziedRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
    }

    private FrenziedRaptor(final FrenziedRaptor card) {
        super(card);
    }

    @Override
    public FrenziedRaptor copy() {
        return new FrenziedRaptor(this);
    }
}
