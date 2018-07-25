package mage.cards.s;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SpawnBroodling extends CardImpl {

    public SpawnBroodling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{G}");
        

        // Destroy target nonartifact creature, then put a 1/1 green Zerg creature token onto the battlefield.
    }

    public SpawnBroodling(final SpawnBroodling card) {
        super(card);
    }

    @Override
    public SpawnBroodling copy() {
        return new SpawnBroodling(this);
    }
}
