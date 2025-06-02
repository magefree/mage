package mage.cards.k;

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
public final class KalakscionHungerTyrant extends CardImpl {

    public KalakscionHungerTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(2);
    }

    private KalakscionHungerTyrant(final KalakscionHungerTyrant card) {
        super(card);
    }

    @Override
    public KalakscionHungerTyrant copy() {
        return new KalakscionHungerTyrant(this);
    }
}
