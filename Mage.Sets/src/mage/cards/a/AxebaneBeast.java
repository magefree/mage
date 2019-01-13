package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class AxebaneBeast extends CardImpl {

    public AxebaneBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
    }

    private AxebaneBeast(final AxebaneBeast card) {
        super(card);
    }

    @Override
    public AxebaneBeast copy() {
        return new AxebaneBeast(this);
    }
}
