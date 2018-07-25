package mage.cards.s;

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
public final class Stalker extends CardImpl {

    public Stalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Stalker becomes blocked by a creature with power 2 or greater, return Stalker to its owner's hand.
    }

    public Stalker(final Stalker card) {
        super(card);
    }

    @Override
    public Stalker copy() {
        return new Stalker(this);
    }
}
