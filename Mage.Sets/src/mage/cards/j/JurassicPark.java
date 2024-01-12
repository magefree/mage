package mage.cards.j;

import java.util.UUID;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jimga150
 */
public final class JurassicPark extends CardImpl {

    public JurassicPark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.supertype.add(SuperType.LEGENDARY);

        // (Transforms from Welcome to ....)
        // Each Dinosaur card in your graveyard has escape. The escape cost is equal to the card's mana cost plus exile three other cards from your graveyard.
        // {T}: Add {G} for each Dinosaur you control.
    }

    private JurassicPark(final JurassicPark card) {
        super(card);
    }

    @Override
    public JurassicPark copy() {
        return new JurassicPark(this);
    }
}
