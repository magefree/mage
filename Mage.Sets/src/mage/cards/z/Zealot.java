package mage.cards.z;

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
public final class Zealot extends CardImpl {

    public Zealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Psionic 1
        // {1}, Remove a psi counter from Zealot: Prevent all damage that would be dealt to Zealot this turn.
    }

    public Zealot(final Zealot card) {
        super(card);
    }

    @Override
    public Zealot copy() {
        return new Zealot(this);
    }
}
