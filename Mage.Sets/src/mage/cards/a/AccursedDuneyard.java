package mage.cards.a;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author sobiech
 */
public final class AccursedDuneyard extends CardImpl {

    public AccursedDuneyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // {T}: Add {C}.
        // {2}, {T}: Regenerate target Shade, Skeleton, Specter, Spirit, Vampire, Wraith, or Zombie.
    }

    private AccursedDuneyard(final AccursedDuneyard card) {
        super(card);
    }

    @Override
    public AccursedDuneyard copy() {
        return new AccursedDuneyard(this);
    }
}
