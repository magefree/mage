
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class DictateOfHeliod extends CardImpl {

    public DictateOfHeliod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{W}");


        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2,2,Duration.WhileOnBattlefield)));
    }

    private DictateOfHeliod(final DictateOfHeliod card) {
        super(card);
    }

    @Override
    public DictateOfHeliod copy() {
        return new DictateOfHeliod(this);
    }
}
