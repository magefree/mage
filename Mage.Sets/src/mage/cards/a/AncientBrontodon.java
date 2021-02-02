
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author caldover
 */
public final class AncientBrontodon extends CardImpl {

    public AncientBrontodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);
    }

    private AncientBrontodon(final AncientBrontodon card) {
        super(card);
    }

    @Override
    public AncientBrontodon copy() {
        return new AncientBrontodon(this);
    }
}
