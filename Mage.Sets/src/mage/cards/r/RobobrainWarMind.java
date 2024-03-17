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
public final class RobobrainWarMind extends CardImpl {

    public RobobrainWarMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(*);
        this.toughness = new MageInt(5);

        // Robobrain War Mind's power is equal to the number of cards in your hand.
        //  Robobrain War Mind enters the battlefield, you get an amount of {E} equal to the number of artifact creatures you control.
        // Whenever Robobrain War Mind attacks, you may pay {E}{E}{E}. If you do, draw a card.
    }

    private RobobrainWarMind(final RobobrainWarMind card) {
        super(card);
    }

    @Override
    public RobobrainWarMind copy() {
        return new RobobrainWarMind(this);
    }
}
