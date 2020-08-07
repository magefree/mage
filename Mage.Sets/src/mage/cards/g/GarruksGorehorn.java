package mage.cards.g;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarruksGorehorn extends CardImpl {

    public GarruksGorehorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(7);
        this.toughness = new MageInt(3);
    }

    private GarruksGorehorn(final GarruksGorehorn card) {
        super(card);
    }

    @Override
    public GarruksGorehorn copy() {
        return new GarruksGorehorn(this);
    }
}
