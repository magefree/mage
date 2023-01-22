package mage.cards.n;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author AhmadYProjects
 */
public final class NoxiousAssault extends CardImpl {

    public NoxiousAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        

        // Creatures you control get +2/+2 until end of turn. Whenever a creature blocks this turn, its controller gets a poison counter.
    }

    private NoxiousAssault(final NoxiousAssault card) {
        super(card);
    }

    @Override
    public NoxiousAssault copy() {
        return new NoxiousAssault(this);
    }
}
