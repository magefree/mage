package mage.cards.r;

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
public final class Reaper extends CardImpl {

    public Reaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.TERRAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Reaper attacks, target player draws a card, then discards a card at random.
    }

    public Reaper(final Reaper card) {
        super(card);
    }

    @Override
    public Reaper copy() {
        return new Reaper(this);
    }
}
