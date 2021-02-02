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
public final class WildCeratok extends CardImpl {

    public WildCeratok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private WildCeratok(final WildCeratok card) {
        super(card);
    }

    @Override
    public WildCeratok copy() {
        return new WildCeratok(this);
    }
}
