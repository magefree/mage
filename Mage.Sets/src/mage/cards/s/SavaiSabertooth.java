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
public final class SavaiSabertooth extends CardImpl {

    public SavaiSabertooth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private SavaiSabertooth(final SavaiSabertooth card) {
        super(card);
    }

    @Override
    public SavaiSabertooth copy() {
        return new SavaiSabertooth(this);
    }
}
