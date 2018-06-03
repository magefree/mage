
package mage.cards.s;

import java.util.UUID;
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
public final class SwiftKick extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public SwiftKick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Target creature you control gets +1/+0 until end of turn. It fights target creature you don't control.
        Effect effect = new BoostTargetEffect(1, 0, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        effect = new FightTargetsEffect();
        effect.setText("It fights target creature you don't control");
        this.getSpellAbility().addEffect(effect);
        Target target = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(target);

    }

    public SwiftKick(final SwiftKick card) {
        super(card);
    }

    @Override
    public SwiftKick copy() {
        return new SwiftKick(this);
    }
}
