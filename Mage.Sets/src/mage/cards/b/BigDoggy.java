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
public final class BigDoggy extends CardImpl {

    public BigDoggy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

    }

    private BigDoggy(final BigDoggy card) {
        super(card);
    }

    @Override
    public BigDoggy copy() {
        return new BigDoggy(this);
    }
}
