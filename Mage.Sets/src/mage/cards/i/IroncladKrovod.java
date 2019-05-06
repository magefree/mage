package mage.cards.i;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IroncladKrovod extends CardImpl {

    public IroncladKrovod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
    }

    private IroncladKrovod(final IroncladKrovod card) {
        super(card);
    }

    @Override
    public IroncladKrovod copy() {
        return new IroncladKrovod(this);
    }
}
