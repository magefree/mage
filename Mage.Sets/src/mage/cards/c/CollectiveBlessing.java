
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class CollectiveBlessing extends CardImpl {

    public CollectiveBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}{G}{W}");


        // Creatures you control get +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(3,3, Duration.WhileOnBattlefield)));
    }

    private CollectiveBlessing(final CollectiveBlessing card) {
        super(card);
    }

    @Override
    public CollectiveBlessing copy() {
        return new CollectiveBlessing(this);
    }
}