package mage.cards.b;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class BasrisAegis extends CardImpl {

    public BasrisAegis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
        

        // Put a +1/+1 counter on each of up to two target creatures. You may search your library and/or graveyard for a card named Basri, Devoted Paladin, reveal it, and put it into your hand. If you search your library this way, shuffle it.
    }

    private BasrisAegis(final BasrisAegis card) {
        super(card);
    }

    @Override
    public BasrisAegis copy() {
        return new BasrisAegis(this);
    }
}
