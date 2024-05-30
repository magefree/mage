package mage.cards.v;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class VexingBauble extends CardImpl {

    public VexingBauble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        

        // Whenever a player casts a spell, if no mana was spent to cast it, counter that spell.
        // {1}, {T}, Sacrifice Vexing Bauble: Draw a card.
    }

    private VexingBauble(final VexingBauble card) {
        super(card);
    }

    @Override
    public VexingBauble copy() {
        return new VexingBauble(this);
    }
}
