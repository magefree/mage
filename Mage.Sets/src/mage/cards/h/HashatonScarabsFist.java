package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author sobiech
 */
public final class HashatonScarabsFist extends CardImpl {

    public HashatonScarabsFist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you discard a creature card, you may pay {2}{U}. If you do, create a tapped token that's a copy of that card, except it's a 4/4 black Zombie.
    }

    private HashatonScarabsFist(final HashatonScarabsFist card) {
        super(card);
    }

    @Override
    public HashatonScarabsFist copy() {
        return new HashatonScarabsFist(this);
    }
}
