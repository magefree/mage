package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HulkingBugbear extends CardImpl {

    public HulkingBugbear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private HulkingBugbear(final HulkingBugbear card) {
        super(card);
    }

    @Override
    public HulkingBugbear copy() {
        return new HulkingBugbear(this);
    }
}
