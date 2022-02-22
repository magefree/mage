
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author mschatz
 */
public final class DreadOfNightB extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public DreadOfNightB(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, -1, Duration.WhileOnBattlefield, filter, false)));
    }

    private DreadOfNightB(final DreadOfNightB card) {
        super(card);
    }

    @Override
    public DreadOfNightB copy() {
        return new DreadOfNightB(this);
    }
}
