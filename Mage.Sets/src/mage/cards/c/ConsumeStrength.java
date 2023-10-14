
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ConsumeStrength extends CardImpl {

    public ConsumeStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{G}");

        // Target creature gets +2/+2 until end of turn. Another target creature gets -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new ConsumeStrengthEffect());

        FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature to get +2/+2");
        TargetCreaturePermanent target1 = new TargetCreaturePermanent(filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1);

        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another creature to get -2/-2");
        filter2.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private ConsumeStrength(final ConsumeStrength card) {
        super(card);
    }

    @Override
    public ConsumeStrength copy() {
        return new ConsumeStrength(this);
    }
}

class ConsumeStrengthEffect extends OneShotEffect {

    public ConsumeStrengthEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Target creature gets +2/+2 until end of turn. Another target creature gets -2/-2 until end of turn";
    }

    private ConsumeStrengthEffect(final ConsumeStrengthEffect effect) {
        super(effect);
    }

    @Override
    public ConsumeStrengthEffect copy() {
        return new ConsumeStrengthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            ContinuousEffect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            ContinuousEffect effect = new BoostTargetEffect(-2, -2, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
