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
public final class SanctumOfFruitfulHarvest extends CardImpl {

    public SanctumOfFruitfulHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your precombat main phase, add X mana of any one color, where X is the number of Shrines you control.
    }

    private SanctumOfFruitfulHarvest(final SanctumOfFruitfulHarvest card) {
        super(card);
    }

    @Override
    public SanctumOfFruitfulHarvest copy() {
        return new SanctumOfFruitfulHarvest(this);
    }
}
