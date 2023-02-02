package mage.cards.c;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author AhmadYProjects
 */
public final class ChargeOfTheMites extends CardImpl {

    public ChargeOfTheMites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");
        

        // Choose one--
        // * Charge of the Mites deals damage equal to the number of creatures you control to target creature or planeswalker.
        // * Create two 1/1 colorless Phyrexian Mite artifact creature tokens with toxic 1 and "This creature can't block."
    }

    private ChargeOfTheMites(final ChargeOfTheMites card) {
        super(card);
    }

    @Override
    public ChargeOfTheMites copy() {
        return new ChargeOfTheMites(this);
    }
}
