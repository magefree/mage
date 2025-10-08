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
public final class LittlePenguin extends CardImpl {

    public LittlePenguin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

    }

    private LittlePenguin(final LittlePenguin card) {
        super(card);
    }

    @Override
    public LittlePenguin copy() {
        return new LittlePenguin(this);
    }
}