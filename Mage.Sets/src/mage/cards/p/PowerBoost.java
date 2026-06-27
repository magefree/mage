package mage.cards.p;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class PowerBoost extends CardImpl {

    public PowerBoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield)));
    }

    private PowerBoost(final PowerBoost card) {
        super(card);
    }

    @Override
    public PowerBoost copy() {
        return new PowerBoost(this);
    }
}
