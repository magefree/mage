package mage.cards.b;

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
public final class BelenonWarAnthem extends CardImpl {

    public BelenonWarAnthem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.color.setWhite(true);
        this.nightCard = true;

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    private BelenonWarAnthem(final BelenonWarAnthem card) {
        super(card);
    }

    @Override
    public BelenonWarAnthem copy() {
        return new BelenonWarAnthem(this);
    }
}
