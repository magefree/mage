package mage.cards.p;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class PlasmaCaster extends CardImpl {

    public PlasmaCaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        // Whenever equipped creature attacks, you get {E}{E}.
        // Pay {E}{E}: Choose target creature that's blocking equipped creature. Flip a coin. If you win the flip, exile the chosen creature. Otherwise, Plasma Caster deals 1 damage to it.
        // Equip {2}
    }

    private PlasmaCaster(final PlasmaCaster card) {
        super(card);
    }

    @Override
    public PlasmaCaster copy() {
        return new PlasmaCaster(this);
    }
}
