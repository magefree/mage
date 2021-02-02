
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class Alarum extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonattacking creature");

    static {
        filter.add(Predicates.not(AttackingPredicate.instance));
    }

    public Alarum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Untap target nonattacking creature. It gets +1/+3 until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        Effect effect = new BoostTargetEffect(1, 3, Duration.EndOfTurn);
        effect.setText("It gets +1/+3 until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private Alarum(final Alarum card) {
        super(card);
    }

    @Override
    public Alarum copy() {
        return new Alarum(this);
    }
}
