package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author ChesseTheWasp
 */
public final class BigSnail extends CardImpl {

    public BigSnail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

    }

    private BigSnail(final BigSnail card) {
        super(card);
    }

    @Override
    public BigSnail copy() {
        return new BigSnail(this);
    }
}
