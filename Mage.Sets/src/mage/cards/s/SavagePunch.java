
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SavagePunch extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public SavagePunch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // <i>Ferocious</i> &mdash; The creature you control gets +2/+2 until end of turn before it fights if you control a creature with power 4 or greater.
        Effect effect = new ConditionalContinuousEffect(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn),
                new LockedInCondition(FerociousCondition.instance),
                "<i>Ferocious</i> &mdash; The creature you control gets +2/+2 until end of turn before it fights if you control a creature with power 4 or greater");
        this.getSpellAbility().addEffect(effect);

        // Target creature you control fights target creature you don't control.
        effect = new FightTargetsEffect();
        effect.setText("<br/>Target creature you control fights target creature you don't control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Target target = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(target);
    }

    public SavagePunch(final SavagePunch card) {
        super(card);
    }

    @Override
    public SavagePunch copy() {
        return new SavagePunch(this);
    }
}
