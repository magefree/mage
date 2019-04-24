
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Styxo
 */
public final class EarthSurge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("land creatures");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public EarthSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");

        //Each land gets +2/+2 as long as it's a creature.
        Effect effect = new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, true);
        effect.setText("Each land gets +2/+2 as long as it\'s a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public EarthSurge(final EarthSurge card) {
        super(card);
    }

    @Override
    public EarthSurge copy() {
        return new EarthSurge(this);
    }
}
