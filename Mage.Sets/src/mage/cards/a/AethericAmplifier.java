package mage.cards.a;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author sobiech
 */
public final class AethericAmplifier extends CardImpl {

    public AethericAmplifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        

        // {T}: Add one mana of any color.
        // {4}, {T}: Choose one. Activate only as a sorcery.
        // * Double the number of each kind of counter on target permanent.
        // * Double the number of each kind of counter you have.
    }

    private AethericAmplifier(final AethericAmplifier card) {
        super(card);
    }

    @Override
    public AethericAmplifier copy() {
        return new AethericAmplifier(this);
    }
}
