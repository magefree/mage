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
public final class SpinedKarok extends CardImpl {

    public SpinedKarok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
    }

    private SpinedKarok(final SpinedKarok card) {
        super(card);
    }

    @Override
    public SpinedKarok copy() {
        return new SpinedKarok(this);
    }
}
