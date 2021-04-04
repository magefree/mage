package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class AcademicDebate extends CardImpl {

    public AcademicDebate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature blocks this turn if able. You may have that creature gain reach until end of turn.
        this.getSpellAbility().addEffect(new BlocksIfAbleTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new AcademicDebateEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
    }

    private AcademicDebate(final AcademicDebate card) {
        super(card);
    }

    @Override
    public AcademicDebate copy() {
        return new AcademicDebate(this);
    }
}

class AcademicDebateEffect extends OneShotEffect {

    AcademicDebateEffect() {
        super(Outcome.AddAbility);
        this.staticText = "You may have that creature gain reach until end of turn";
    }

    private AcademicDebateEffect(final AcademicDebateEffect effect) {
        super(effect);
    }

    @Override
    public AcademicDebateEffect copy() {
        return new AcademicDebateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Permanent permanent = game.getPermanent(this.targetPointer.getFirst(game, source));
            if (permanent != null) {
                if (player.chooseUse(outcome, "Have " + permanent.getLogName() + " gain reach until end of turn?", source, game)) {
                    GainAbilityTargetEffect effect = new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
                    game.addEffect(effect, source);
                    return true;
                }
            }
        }
        return false;
    }
}
