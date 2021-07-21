package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.OpenSideboardHint;
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
public final class AcademicDispute extends CardImpl {

    public AcademicDispute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature blocks this turn if able. You may have that creature gain reach until end of turn.
        this.getSpellAbility().addEffect(new BlocksIfAbleTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new AcademicDisputeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private AcademicDispute(final AcademicDispute card) {
        super(card);
    }

    @Override
    public AcademicDispute copy() {
        return new AcademicDispute(this);
    }
}

class AcademicDisputeEffect extends OneShotEffect {

    AcademicDisputeEffect() {
        super(Outcome.AddAbility);
        this.staticText = "You may have it gain reach until end of turn";
    }

    private AcademicDisputeEffect(final AcademicDisputeEffect effect) {
        super(effect);
    }

    @Override
    public AcademicDisputeEffect copy() {
        return new AcademicDisputeEffect(this);
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
