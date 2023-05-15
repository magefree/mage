package mage.cards.b;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Arti
 */
public final class BalloonStand extends CardImpl {

    public BalloonStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        
        //this.subtype.add(SubType.ATTRACTION);

        // Visit -- Choose one.
        // * Create a 1/1 red Balloon creature token with flying.
        // * Sacrifice a Balloon. If you do, target creature gains flying until end of turn.
    }

    private BalloonStand(final BalloonStand card) {
        super(card);
    }

    @Override
    public BalloonStand copy() {
        return new BalloonStand(this);
    }

    @Override
    public boolean isExtraDeckCard() {
        return true;
    }
}
