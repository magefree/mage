
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageWithPowerTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ciaccona007
 */
public final class Ambuscade extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public Ambuscade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature you control gets +1/+0 until end of turn.
        Effect effect = new BoostTargetEffect(1, 0, Duration.EndOfTurn);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(effect);

        // It deals damage equal to its power to target creature you don't control.
        effect = new DamageWithPowerTargetEffect();
        effect.setText("It deals damage equal to its power to target creature you don't control");
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(effect);
    }

    public Ambuscade(final Ambuscade card) {
        super(card);
    }

    @Override
    public Ambuscade copy() {
        return new Ambuscade(this);
    }
}
