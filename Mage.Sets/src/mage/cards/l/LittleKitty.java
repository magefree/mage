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
public final class LittleKitty extends CardImpl {

    public LittleKitty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

    }

    private LittleKitty(final LittleKitty card) {
        super(card);
    }

    @Override
    public LittleKitty copy() {
        return new LittleKitty(this);
    }
}