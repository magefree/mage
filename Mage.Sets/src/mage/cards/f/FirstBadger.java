package mage.cards.f;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import java.util.UUID;

/**
 * @author ChesseTheWasp
 */
public final class FirstBadger extends CardImpl {

    public FirstBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT,CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.BADGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private FirstBadger(final FirstBadger card) {
        super(card);
    }

    @Override
    public FirstBadger copy() {
        return new FirstBadger(this);
    }
}