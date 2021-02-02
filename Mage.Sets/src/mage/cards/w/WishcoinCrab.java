package mage.cards.w;

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
public final class WishcoinCrab extends CardImpl {

    public WishcoinCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
    }

    private WishcoinCrab(final WishcoinCrab card) {
        super(card);
    }

    @Override
    public WishcoinCrab copy() {
        return new WishcoinCrab(this);
    }
}
