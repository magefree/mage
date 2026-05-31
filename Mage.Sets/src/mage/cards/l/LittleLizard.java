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
public final class LittleLizard extends CardImpl {

    public LittleLizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

    }

    private LittleLizard(final LittleLizard card) {
        super(card);
    }

    @Override
    public LittleLizard copy() {
        return new LittleLizard(this);
    }
}