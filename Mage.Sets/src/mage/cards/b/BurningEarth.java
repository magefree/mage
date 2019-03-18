
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.TapForManaAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class BurningEarth extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a player taps a nonbasic land");
    
    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
    }
    
    public BurningEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // Whenever a player taps a nonbasic land for mana, Burning Earth deals 1 damage to that player.
        this.addAbility(new TapForManaAllTriggeredAbility(
                new DamageTargetEffect(1, true, "that player"),
                filter, SetTargetPointer.PLAYER));
    }

    public BurningEarth(final BurningEarth card) {
        super(card);
    }

    @Override
    public BurningEarth copy() {
        return new BurningEarth(this);
    }
}
