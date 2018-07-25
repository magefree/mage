package mage.cards.m;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class MineralField extends CardImpl {

    public MineralField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // {T}, Sacrifice Mineral Field: Search your library for a basic land card and put it onto the battlefield tapped. Then shuffle your library.
    }

    public MineralField(final MineralField card) {
        super(card);
    }

    @Override
    public MineralField copy() {
        return new MineralField(this);
    }
}
