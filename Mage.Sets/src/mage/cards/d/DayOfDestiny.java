
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class DayOfDestiny extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Legendary creatures");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public DayOfDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);

        // Legendary creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, false)));
    }

    private DayOfDestiny(final DayOfDestiny card) {
        super(card);
    }

    @Override
    public DayOfDestiny copy() {
        return new DayOfDestiny(this);
    }
}
