package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HappyHoganDauntlessDriver extends CardImpl {

    public HappyHoganDauntlessDriver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private HappyHoganDauntlessDriver(final HappyHoganDauntlessDriver card) {
        super(card);
    }

    @Override
    public HappyHoganDauntlessDriver copy() {
        return new HappyHoganDauntlessDriver(this);
    }
}
