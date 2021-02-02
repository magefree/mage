
package mage.cards.c;

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
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author hanasu
 */
public final class Crosswinds extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Creatures with flying");
    
    static {
        filter1.add(new AbilityPredicate(FlyingAbility.class));
    }
    
    public Crosswinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // Creatures with flying get -2/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-2, 0, Duration.WhileOnBattlefield, filter1, false)));
    }

    private Crosswinds(final Crosswinds card) {
        super(card);
    }

    @Override
    public Crosswinds copy() {
        return new Crosswinds(this);
    }
}
