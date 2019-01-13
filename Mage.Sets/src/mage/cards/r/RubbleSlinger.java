package mage.cards.r;

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
public final class RubbleSlinger extends CardImpl {

    public RubbleSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private RubbleSlinger(final RubbleSlinger card) {
        super(card);
    }

    @Override
    public RubbleSlinger copy() {
        return new RubbleSlinger(this);
    }
}
