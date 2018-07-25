package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Marauder extends CardImpl {

    public Marauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Marauder attacks, you may pay {1}{W}. If you do, tap target creature.
    }

    public Marauder(final Marauder card) {
        super(card);
    }

    @Override
    public Marauder copy() {
        return new Marauder(this);
    }
}
