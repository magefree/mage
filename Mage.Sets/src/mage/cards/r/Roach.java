package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Roach extends CardImpl {

    public Roach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {5}{B}: Return Roach from your graveyard to the battlefield tapped.
    }

    public Roach(final Roach card) {
        super(card);
    }

    @Override
    public Roach copy() {
        return new Roach(this);
    }
}
