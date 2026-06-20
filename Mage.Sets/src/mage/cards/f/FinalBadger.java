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
public final class FinalBadger extends CardImpl {

    public FinalBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT,CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.BADGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);
    }

    private FinalBadger(final FinalBadger card) {
        super(card);
    }

    @Override
    public FinalBadger copy() {
        return new FinalBadger(this);
    }
}