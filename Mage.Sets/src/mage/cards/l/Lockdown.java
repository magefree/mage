package mage.cards.l;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Lockdown extends CardImpl {

    public Lockdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
        

        // Tap all creatures target player controls. Those creatures don't untap during that player's next untap step.
    }

    public Lockdown(final Lockdown card) {
        super(card);
    }

    @Override
    public Lockdown copy() {
        return new Lockdown(this);
    }
}
