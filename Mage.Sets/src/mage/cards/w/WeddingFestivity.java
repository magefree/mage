package mage.cards.w;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public class WeddingFestivity extends CardImpl {

    public WeddingFestivity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.color.setWhite(true);
        this.nightCard = true;

        // Creatures you control get +1/+1
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    private WeddingFestivity(final WeddingFestivity card) {
        super(card);
    }

    @Override
    public WeddingFestivity copy() {
        return new WeddingFestivity(this);
    }
}
