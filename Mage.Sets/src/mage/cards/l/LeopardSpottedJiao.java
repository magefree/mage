package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class LeopardSpottedJiao extends CardImpl {

    public LeopardSpottedJiao(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private LeopardSpottedJiao(final LeopardSpottedJiao card) {
        super(card);
    }

    @Override
    public LeopardSpottedJiao copy() {
        return new LeopardSpottedJiao(this);
    }
}
