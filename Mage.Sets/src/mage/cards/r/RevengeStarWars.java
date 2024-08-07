package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouLostLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class RevengeStarWars extends CardImpl {

    private static final Condition condition = new YouLostLifeCondition();

    public RevengeStarWars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature you control gets +4/+0 until end of turn before it fights if you lost life this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new RevengeEffect(), condition,
                "Target creature you control gets +4/+0 until end of turn before it fights if you lost life this turn"));

        // Target creature you control fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

    }

    private RevengeStarWars(final RevengeStarWars card) {
        super(card);
    }

    @Override
    public RevengeStarWars copy() {
        return new RevengeStarWars(this);
    }
}

class RevengeEffect extends OneShotEffect {

    RevengeEffect() {
        super(Outcome.BoostCreature);
    }

    private RevengeEffect(final RevengeEffect effect) {
        super(effect);
    }

    @Override
    public RevengeEffect copy() {
        return new RevengeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target != null && target.isCreature(game)) {
            ContinuousEffect effect = new BoostTargetEffect(4, 0, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(target.getId(), game));
            game.addEffect(effect, source);
            return true;
        }

        return false;
    }

}
