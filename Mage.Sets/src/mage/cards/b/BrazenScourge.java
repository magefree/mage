
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class BrazenScourge extends CardImpl {

    public BrazenScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.GREMLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private BrazenScourge(final BrazenScourge card) {
        super(card);
    }

    @Override
    public BrazenScourge copy() {
        return new BrazenScourge(this);
    }
}
