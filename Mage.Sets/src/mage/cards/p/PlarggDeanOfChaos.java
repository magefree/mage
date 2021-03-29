package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class PlarggDeanOfChaos extends CardImpl {

    public PlarggDeanOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}, Discard a card: Draw a card.
        // {4}{R}, {T}: Reveal cards from the top of your library until you reveal a nonlegendary, nonland card with mana value 3 or less. You may cast that card without paying its mana cost. Put all revealed cards not cast this way on the bottom of your library in a random order.
    }

    private PlarggDeanOfChaos(final PlarggDeanOfChaos card) {
        super(card);
    }

    @Override
    public PlarggDeanOfChaos copy() {
        return new PlarggDeanOfChaos(this);
    }
}
