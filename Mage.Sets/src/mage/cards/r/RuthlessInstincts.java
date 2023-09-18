
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RuthlessInstincts extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonattacking creature");
    private static final FilterCreaturePermanent filterAttacking = new FilterCreaturePermanent("attacking creature");

    static {
        filter.add(Predicates.not(AttackingPredicate.instance));
        filterAttacking.add(AttackingPredicate.instance);
    }

    public RuthlessInstincts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Choose one -
        // * Target nonattacking creature gains reach and deathtouch until end of turn. Untap it.
        Effect effect = new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Target nonattacking creature gains reach");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and deathtouch until end of turn");
        this.getSpellAbility().addEffect(effect);
        effect = new UntapTargetEffect();
        effect.setText("Untap it");
        this.getSpellAbility().addEffect(effect);
        Target target = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(target);
        // * Target attacking creature gets +2/+2 and gains trample until end of turn.
        effect = new BoostTargetEffect(2,2,Duration.EndOfTurn);
        effect.setText("Target attacking creature gets +2/+2");
        Mode mode = new Mode(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        mode.addEffect(effect);
        mode.addTarget(new TargetCreaturePermanent(filterAttacking));
        this.getSpellAbility().addMode(mode);
    }

    private RuthlessInstincts(final RuthlessInstincts card) {
        super(card);
    }

    @Override
    public RuthlessInstincts copy() {
        return new RuthlessInstincts(this);
    }
}
