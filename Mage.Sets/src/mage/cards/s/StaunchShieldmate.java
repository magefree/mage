package mage.cards.s;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StaunchShieldmate extends CardImpl {

    public StaunchShieldmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
    }

    private StaunchShieldmate(final StaunchShieldmate card) {
        super(card);
    }

    @Override
    public StaunchShieldmate copy() {
        return new StaunchShieldmate(this);
    }
}
