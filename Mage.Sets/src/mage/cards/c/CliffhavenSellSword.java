package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class CliffhavenSellSword extends CardImpl {

    public CliffhavenSellSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private CliffhavenSellSword(final CliffhavenSellSword card) {
        super(card);
    }

    @Override
    public CliffhavenSellSword copy() {
        return new CliffhavenSellSword(this);
    }
}
