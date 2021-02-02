
package mage.cards.b;

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
 * @author jeffwadsworth
 */
public final class BadMoon extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public BadMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");


        // Black creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));
        
    }

    private BadMoon(final BadMoon card) {
        super(card);
    }

    @Override
    public BadMoon copy() {
        return new BadMoon(this);
    }
}
