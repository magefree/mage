package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElminstersSimulacrum extends CardImpl {

    public ElminstersSimulacrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // For each opponent, you create a token that's a copy of up to one target creature that player controls.
        this.getSpellAbility().addEffect(new ElminstersSimulacrumAdjusterEffect());
        this.getSpellAbility().setTargetAdjuster(ElminstersSimulacrumAdjuster.instance);
    }

    private ElminstersSimulacrum(final ElminstersSimulacrum card) {
        super(card);
    }

    @Override
    public ElminstersSimulacrum copy() {
        return new ElminstersSimulacrum(this);
    }
}

enum ElminstersSimulacrumAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + opponent.getLogName());
            filter.add(new ControllerIdPredicate(opponentId));
            ability.addTarget(new TargetPermanent(0, 1, filter, false));
        }
    }
}

class ElminstersSimulacrumAdjusterEffect extends OneShotEffect {

    ElminstersSimulacrumAdjusterEffect() {
        super(Outcome.Benefit);
        this.setTargetPointer(new EachTargetPointer());
        staticText = "for each opponent, you create a token that's a copy of up to one target creature that player controls";
    }

    private ElminstersSimulacrumAdjusterEffect(final ElminstersSimulacrumAdjusterEffect effect) {
        super(effect);
    }

    @Override
    public ElminstersSimulacrumAdjusterEffect copy() {
        return new ElminstersSimulacrumAdjusterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
            effect.setSavedPermanent(permanent);
            effect.apply(game, source);
        }
        return true;
    }
}
