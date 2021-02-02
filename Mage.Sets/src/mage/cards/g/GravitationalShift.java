
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class GravitationalShift extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Creatures with flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Creatures without flying");

    static {
        filter1.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public GravitationalShift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");


        // Creatures with flying get +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(2, 0, Duration.WhileOnBattlefield, filter1, false)));

        // Creatures without flying get -2/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-2, 0, Duration.WhileOnBattlefield, filter2, false)));
    }

    private GravitationalShift(final GravitationalShift card) {
        super(card);
    }

    @Override
    public GravitationalShift copy() {
        return new GravitationalShift(this);
    }
}
