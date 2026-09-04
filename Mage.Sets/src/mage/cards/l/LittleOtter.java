package mage.cards.l;

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
public final class LittleOtter extends CardImpl {

    public LittleOtter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

    }

    private LittleOtter(final LittleOtter card) {
        super(card);
    }

    @Override
    public LittleOtter copy() {
        return new LittleOtter(this);
    }
}