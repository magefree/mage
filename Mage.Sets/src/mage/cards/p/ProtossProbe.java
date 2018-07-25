package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class ProtossProbe extends CardImpl {

    public ProtossProbe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{U}, {T}: Draw a card.
    }

    public ProtossProbe(final ProtossProbe card) {
        super(card);
    }

    @Override
    public ProtossProbe copy() {
        return new ProtossProbe(this);
    }
}
