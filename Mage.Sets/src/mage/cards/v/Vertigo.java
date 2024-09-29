
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class Vertigo extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Vertigo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Vertigo deals 2 damage to target creature with flying. That creature loses flying until end of turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                .setText("That creature loses flying until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private Vertigo(final Vertigo card) {
        super(card);
    }

    @Override
    public Vertigo copy() {
        return new Vertigo(this);
    }
}
