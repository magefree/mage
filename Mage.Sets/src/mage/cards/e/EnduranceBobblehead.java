package mage.cards.e;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class EnduranceBobblehead extends CardImpl {

    public EnduranceBobblehead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.subtype.add(SubType.BOBBLEHEAD);

        // {T}: Add one mana of any color.
        // {3}, {T}: Up to X target creatures you control get +1/+0 and gain indestructible until end of turn, where X is the number of Bobbleheads you control as you activate this ability. Activate only as a sorcery.
    }

    private EnduranceBobblehead(final EnduranceBobblehead card) {
        super(card);
    }

    @Override
    public EnduranceBobblehead copy() {
        return new EnduranceBobblehead(this);
    }
}
