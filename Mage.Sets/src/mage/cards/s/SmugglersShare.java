package mage.cards.s;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SmugglersShare extends CardImpl {

    public SmugglersShare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        

        // At the beginning of each end step, draw a card for each opponent who drew two or more cards this turn, then create a Treasure token for each opponent who had two or more lands enter the battlefield under their control this turn.
    }

    private SmugglersShare(final SmugglersShare card) {
        super(card);
    }

    @Override
    public SmugglersShare copy() {
        return new SmugglersShare(this);
    }
}
