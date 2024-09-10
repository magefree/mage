
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author ilcartographer
 */
public final class SerraAviary extends CardImpl {
    
    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Creatures with flying");
    
    static {
        filter1.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SerraAviary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");
        this.supertype.add(SuperType.WORLD);

        // Creatures with flying get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter1, false)));
    }

    private SerraAviary(final SerraAviary card) {
        super(card);
    }

    @Override
    public SerraAviary copy() {
        return new SerraAviary(this);
    }
}
