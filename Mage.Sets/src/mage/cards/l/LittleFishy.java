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
public final class LittleFishy extends CardImpl {

    public LittleFishy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

    }

    private LittleFishy(final LittleFishy card) {
        super(card);
    }

    @Override
    public LittleFishy copy() {
        return new LittleFishy(this);
    }
}