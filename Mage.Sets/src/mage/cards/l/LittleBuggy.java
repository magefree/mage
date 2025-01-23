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
public final class LittleBuggy extends CardImpl {

    public LittleBuggy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

    }

    private LittleBuggy(final LittleBuggy card) {
        super(card);
    }

    @Override
    public LittleBuggy copy() {
        return new LittleBuggy(this);
    }
}