package mage.cards.v;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Vault112SadisticSimulation extends CardImpl {

    public Vault112SadisticSimulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{R}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters the battlefield and after your draw step, add a lore counter. Sacrifice after III.)
        // I, II -- Tap up to one target creature and put a stun counter on it. You get {E}{E}.
        // III -- Pay any amount of {E}. If you paid one or more {E} this way, shuffle your library, then exile that many cards from the top. You may play one of those cards without paying its mana cost.
    }

    private Vault112SadisticSimulation(final Vault112SadisticSimulation card) {
        super(card);
    }

    @Override
    public Vault112SadisticSimulation copy() {
        return new Vault112SadisticSimulation(this);
    }
}
