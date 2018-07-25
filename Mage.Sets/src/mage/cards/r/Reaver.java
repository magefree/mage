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
public final class Reaver extends CardImpl {

    public Reaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Psionic 1
        // {1}, Remove a psi counter from Reaver: Reaver gets +2/+0 and gains first strike until end of turn.
        // {T}: Put a psi counter on Reaver.
    }

    public Reaver(final Reaver card) {
        super(card);
    }

    @Override
    public Reaver copy() {
        return new Reaver(this);
    }
}
