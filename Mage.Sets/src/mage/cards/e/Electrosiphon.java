package mage.cards.e;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Electrosiphon extends CardImpl {

    public Electrosiphon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}{R}");
        

        // Instant Counter target spell. You get an amount of {E} equal to its mana value.
    }

    private Electrosiphon(final Electrosiphon card) {
        super(card);
    }

    @Override
    public Electrosiphon copy() {
        return new Electrosiphon(this);
    }
}
