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
public final class ImperialOutrider extends CardImpl {

    public ImperialOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);
    }

    private ImperialOutrider(final ImperialOutrider card) {
        super(card);
    }

    @Override
    public ImperialOutrider copy() {
        return new ImperialOutrider(this);
    }
}
