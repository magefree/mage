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
public final class LittleDoggy extends CardImpl {

    public LittleDoggy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

    }

    private LittleDoggy(final LittleDoggy card) {
        super(card);
    }

    @Override
    public LittleDoggy copy() {
        return new LittleDoggy(this);
    }
}