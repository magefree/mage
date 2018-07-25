package mage.cards.t;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class TemplarArchives extends CardImpl {

    public TemplarArchives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Templar Archives enters the battlefield tapped.
        // When Templar Archives enters the battlefield, draw a card, then discard a card.
        // {T}: Add {U} to your mana pool.
    }

    public TemplarArchives(final TemplarArchives card) {
        super(card);
    }

    @Override
    public TemplarArchives copy() {
        return new TemplarArchives(this);
    }
}
