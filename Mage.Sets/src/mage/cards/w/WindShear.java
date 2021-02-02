
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author L_J
 */
public final class WindShear extends CardImpl {
    
    private static final FilterAttackingCreature filter = new FilterAttackingCreature("Attacking creatures with flying");
    
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WindShear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Attacking creatures with flying get -2/-2 and lose flying until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn, filter, false).setText("Attacking creatures with flying get -2/-2"));
        this.getSpellAbility().addEffect(new LoseAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filter).setText("and lose flying until end of turn"));
    }

    private WindShear(final WindShear card) {
        super(card);
    }

    @Override
    public WindShear copy() {
        return new WindShear(this);
    }
}
