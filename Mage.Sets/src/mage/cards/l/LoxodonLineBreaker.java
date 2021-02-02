
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
public final class LoxodonLineBreaker extends CardImpl {

    public LoxodonLineBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private LoxodonLineBreaker(final LoxodonLineBreaker card) {
        super(card);
    }

    @Override
    public LoxodonLineBreaker copy() {
        return new LoxodonLineBreaker(this);
    }
}
