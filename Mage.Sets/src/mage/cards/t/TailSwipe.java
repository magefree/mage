package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.Targets;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class TailSwipe extends CardImpl {

    public TailSwipe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Choose target creature you control and target creature you don't control.
        // If you cast this spell during your main phase, the creature you control gets +1/+1 until end of turn.
        // Then those creatures fight each other.
        this.getSpellAbility().addEffect(new TailSwipeEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private TailSwipe(final TailSwipe card) {
        super(card);
    }

    @Override
    public TailSwipe copy() {
        return new TailSwipe(this);
    }
}

class TailSwipeEffect extends OneShotEffect {

    public TailSwipeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose target creature you control and target creature you don't control. " +
                "If you cast this spell during your main phase, the creature you control gets +1/+1 until end of turn. " +
                "Then those creatures fight each other. " +
                "<i>(Each deals damage equal to its power to the other.)</i>";
    }

    private TailSwipeEffect(final TailSwipeEffect effect) {
        super(effect);
    }

    @Override
    public TailSwipeEffect copy() {
        return new TailSwipeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Targets targets = source.getTargets();
        if (targets.size() < 2) {
            return false;
        }
        Permanent creature1 = game.getPermanent(targets.get(0).getFirstTarget());
        Permanent creature2 = game.getPermanent(targets.get(1).getFirstTarget());
        if (creature1 == null) {
            return false;
        }
        if (AddendumCondition.instance.apply(game, source)) {
            game.addEffect(new BoostTargetEffect(1, 1)
                    .setTargetPointer(new FixedTarget(creature1, game)), source);
        }
        if (creature2 != null) {
            creature1.fight(creature2, source, game);
        }
        return true;
    }
}
