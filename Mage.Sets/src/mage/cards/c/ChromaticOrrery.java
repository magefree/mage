package mage.cards.c;

import java.util.UUID;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class ChromaticOrrery extends CardImpl {

    public ChromaticOrrery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");
        
        this.addSuperType(SuperType.LEGENDARY);

        // You may spend mana as though it were mana of any color.
        // {T}: Add {C}{C}{C}{C}{C}.
        // {5}, {T}: Draw a card for each color among permanents you control.
    }

    private ChromaticOrrery(final ChromaticOrrery card) {
        super(card);
    }

    @Override
    public ChromaticOrrery copy() {
        return new ChromaticOrrery(this);
    }
}
