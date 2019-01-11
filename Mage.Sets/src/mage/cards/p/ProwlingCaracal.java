package mage.cards.p;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProwlingCaracal extends CardImpl {

    public ProwlingCaracal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private ProwlingCaracal(final ProwlingCaracal card) {
        super(card);
    }

    @Override
    public ProwlingCaracal copy() {
        return new ProwlingCaracal(this);
    }
}
