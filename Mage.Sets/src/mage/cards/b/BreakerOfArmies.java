
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class BreakerOfArmies extends CardImpl {

    public BreakerOfArmies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(10);
        this.toughness = new MageInt(8);

        // All creatures able to block Breaker of Armies do so.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAllSourceEffect(Duration.WhileOnBattlefield)));
    }

    private BreakerOfArmies(final BreakerOfArmies card) {
        super(card);
    }

    @Override
    public BreakerOfArmies copy() {
        return new BreakerOfArmies(this);
    }
}
