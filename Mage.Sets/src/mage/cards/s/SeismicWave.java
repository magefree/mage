package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class SeismicWave extends CardImpl {

    public SeismicWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Seismic Wave deals 2 damage to any target and 1 damage to each nonartifact creature target opponent controls.
        this.getSpellAbility().addTarget(new TargetAnyTarget().withChooseHint("2 damage"));
        this.getSpellAbility().addTarget(new TargetOpponent().withChooseHint("1 damage to each nonartifact creature target opponent controls"));
        this.getSpellAbility().addEffect(new SeismicWaveEffect());
    }

    private SeismicWave(final SeismicWave card) {
        super(card);
    }

    @Override
    public SeismicWave copy() {
        return new SeismicWave(this);
    }
}

class SeismicWaveEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public SeismicWaveEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to any target and 1 damage to each nonartifact creature target opponent controls";
    }

    private SeismicWaveEffect(final SeismicWaveEffect effect) {
        super(effect);
    }

    @Override
    public SeismicWaveEffect copy() {
        return new SeismicWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Targets targets = source.getTargets();
        if (targets.size() < 2) {
            return false;
        }
        UUID firstTarget = targets.get(0).getFirstTarget();
        if (firstTarget != null) {
            Permanent targetPermanent = game.getPermanent(firstTarget);
            if (targetPermanent != null) {
                targetPermanent.damage(2, source, game);
            } else {
                Player targetPlayer = game.getPlayer(firstTarget);
                if (targetPlayer != null) {
                    targetPlayer.damage(2, source, game);
                }
            }
        }
        Target targetOpponent = targets.get(1);
        UUID opponentId = targetOpponent.getFirstTarget();
        if (opponentId != null && targetOpponent.isLegal(source, game)) { // Needs this check in case opponent gets hexproof at instant speed
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, opponentId, game)) {
                permanent.damage(1, source, game);
            }
        }
        return true;
    }
}
