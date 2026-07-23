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
public final class BigRhino extends CardImpl {

    public BigRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

    }

    private BigRhino(final BigRhino card) {
        super(card);
    }

    @Override
    public BigRhino copy() {
        return new BigRhino(this);
    }
}
