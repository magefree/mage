package mage.cards.c;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author AhmadYProjects
 */
public final class CarnivorousCanopy extends CardImpl {

    public CarnivorousCanopy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");
        

        // Destroy target artifact, enchantment, or creature with flying. If that permanent's mana value was 3 or less, proliferate.
    }

    private CarnivorousCanopy(final CarnivorousCanopy card) {
        super(card);
    }

    @Override
    public CarnivorousCanopy copy() {
        return new CarnivorousCanopy(this);
    }
}
