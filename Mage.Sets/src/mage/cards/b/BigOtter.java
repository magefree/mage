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
public final class BigOtter extends CardImpl {

    public BigOtter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

    }

    private BigOtter(final BigOtter card) {
        super(card);
    }

    @Override
    public BigOtter copy() {
        return new BigOtter(this);
    }
}
