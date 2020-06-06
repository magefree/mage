package mage.cards.w;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildwoodPatrol extends CardImpl {

    public WildwoodPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private WildwoodPatrol(final WildwoodPatrol card) {
        super(card);
    }

    @Override
    public WildwoodPatrol copy() {
        return new WildwoodPatrol(this);
    }
}
