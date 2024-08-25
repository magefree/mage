package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachOpponentTargetsAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class MassMutiny extends CardImpl {

    public MassMutiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // For each opponent, gain control of up to one target creature that player controls until end of turn. Untap those creatures. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new MassMutinyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0,1));
        this.getSpellAbility().setTargetAdjuster(new ForEachOpponentTargetsAdjuster());
    }

    private MassMutiny(final MassMutiny card) {
        super(card);
    }

    @Override
    public MassMutiny copy() {
        return new MassMutiny(this);
    }
}

class MassMutinyEffect extends OneShotEffect {

    MassMutinyEffect() {
        super(Outcome.GainControl);
        this.staticText = "For each opponent, gain control of up to one target creature that player controls until end of turn. Untap those creatures. They gain haste until end of turn";
    }

    private MassMutinyEffect(final MassMutinyEffect effect) {
        super(effect);
    }

    @Override
    public MassMutinyEffect copy() {
        return new MassMutinyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (Target target : source.getTargets()) {
            if (target instanceof TargetCreaturePermanent) {
                Permanent targetCreature = game.getPermanent(target.getFirstTarget());
                if (targetCreature != null) {
                    ContinuousEffect effect1 = new GainControlTargetEffect(Duration.EndOfTurn);
                    effect1.setTargetPointer(new FixedTarget(targetCreature.getId(), game));
                    game.addEffect(effect1, source);

                    ContinuousEffect effect2 = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                    effect2.setTargetPointer(new FixedTarget(targetCreature.getId(), game));
                    game.addEffect(effect2, source);

                    targetCreature.untap(game);
                    result = true;
                }
            }
        }
        return result;
    }
}
