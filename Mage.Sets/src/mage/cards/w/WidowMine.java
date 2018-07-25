package mage.cards.w;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class WidowMine extends CardImpl {

    public WidowMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        

        // {T}: Name a card. Activate this ability only any time you can cast a sorcery.
        // {R}, {T}, Sacrifice Widow Mine: Widow Mine deals 4 damage to target creature with the last chosen name.
    }

    public WidowMine(final WidowMine card) {
        super(card);
    }

    @Override
    public WidowMine copy() {
        return new WidowMine(this);
    }
}
