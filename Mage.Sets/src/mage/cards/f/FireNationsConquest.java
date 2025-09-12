package mage.cards.f;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationsConquest extends CardImpl {

    public FireNationsConquest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield)));
    }

    private FireNationsConquest(final FireNationsConquest card) {
        super(card);
    }

    @Override
    public FireNationsConquest copy() {
        return new FireNationsConquest(this);
    }
}
