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
public final class LittleRhino extends CardImpl {

    public LittleRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

    }

    private LittleRhino(final LittleRhino card) {
        super(card);
    }

    @Override
    public LittleRhino copy() {
        return new LittleRhino(this);
    }
}