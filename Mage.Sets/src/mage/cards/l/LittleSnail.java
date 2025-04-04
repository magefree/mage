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
public final class LittleSnail extends CardImpl {

    public LittleSnail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

    }

    private LittleSnail(final LittleSnail card) {
        super(card);
    }

    @Override
    public LittleSnail copy() {
        return new LittleSnail(this);
    }
}