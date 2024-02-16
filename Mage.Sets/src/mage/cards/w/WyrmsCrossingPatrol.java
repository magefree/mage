package mage.cards.w;

import mage.MageInt;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WyrmsCrossingPatrol extends CardImpl {

    public WyrmsCrossingPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Myriad
        this.addAbility(new MyriadAbility());
    }

    private WyrmsCrossingPatrol(final WyrmsCrossingPatrol card) {
        super(card);
    }

    @Override
    public WyrmsCrossingPatrol copy() {
        return new WyrmsCrossingPatrol(this);
    }
}
