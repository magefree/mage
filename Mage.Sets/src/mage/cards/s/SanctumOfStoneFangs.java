package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class SanctumOfStoneFangs extends CardImpl {

    public SanctumOfStoneFangs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your precombat main phase, each opponent loses X life and you gain X life, where X is the number of Shrines you control.
    }

    private SanctumOfStoneFangs(final SanctumOfStoneFangs card) {
        super(card);
    }

    @Override
    public SanctumOfStoneFangs copy() {
        return new SanctumOfStoneFangs(this);
    }
}
