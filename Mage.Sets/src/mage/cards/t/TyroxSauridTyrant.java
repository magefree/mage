package mage.cards.t;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyroxSauridTyrant extends CardImpl {

    public TyroxSauridTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
    }

    private TyroxSauridTyrant(final TyroxSauridTyrant card) {
        super(card);
    }

    @Override
    public TyroxSauridTyrant copy() {
        return new TyroxSauridTyrant(this);
    }
}
