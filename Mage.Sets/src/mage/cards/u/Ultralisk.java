package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Ultralisk extends CardImpl {

    public Ultralisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Protection from noncreature
    }

    public Ultralisk(final Ultralisk card) {
        super(card);
    }

    @Override
    public Ultralisk copy() {
        return new Ultralisk(this);
    }
}
