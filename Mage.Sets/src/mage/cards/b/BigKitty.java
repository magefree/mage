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
public final class BigKitty extends CardImpl {

    public BigKitty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

    }

    private BigKitty(final BigKitty card) {
        super(card);
    }

    @Override
    public BigKitty copy() {
        return new BigKitty(this);
    }
}
