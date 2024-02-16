
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.TargetSpell;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class MemoryLapse extends CardImpl {

    public MemoryLapse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target spell. If that spell is countered this way, put it on top of its owner's library instead of into that player's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.TOP_ANY));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private MemoryLapse(final MemoryLapse card) {
        super(card);
    }

    @Override
    public MemoryLapse copy() {
        return new MemoryLapse(this);
    }
}
