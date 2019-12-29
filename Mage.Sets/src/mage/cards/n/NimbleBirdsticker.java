package mage.cards.n;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NimbleBirdsticker extends CardImpl {

    public NimbleBirdsticker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private NimbleBirdsticker(final NimbleBirdsticker card) {
        super(card);
    }

    @Override
    public NimbleBirdsticker copy() {
        return new NimbleBirdsticker(this);
    }
}
