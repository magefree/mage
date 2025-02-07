package mage.cards.a;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author sobiech
 */
public final class AdaptiveOmnitool extends CardImpl {

    public AdaptiveOmnitool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each artifact you control.
        // Whenever equipped creature attacks, look at the top six cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        // Equip {3}
    }

    private AdaptiveOmnitool(final AdaptiveOmnitool card) {
        super(card);
    }

    @Override
    public AdaptiveOmnitool copy() {
        return new AdaptiveOmnitool(this);
    }
}
