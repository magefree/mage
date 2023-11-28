package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutAllEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class GaladrielsDismissal extends CardImpl {

    public GaladrielsDismissal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Kicker {2}{W}
        this.addAbility(new KickerAbility("{2}{W}"));

        // Target creature phases out. If this spell was kicked, each creature target player controls phases out instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GaladrielsDismissalPhaseOutTargetPlayerEffect(),
                new PhaseOutTargetEffect(),
                KickedCondition.ONCE, "Target creature phases out. If this spell was kicked, each creature target player controls phases out instead. "+
                "<i>(Treat phased-out creatures and anything attached to them as though they don't exist until their controller's next turn.)</i>"
        ));
        this.getSpellAbility().setTargetAdjuster(GaladrielsDismissalAdjuster.instance);

    }

    private GaladrielsDismissal(final GaladrielsDismissal card) {
        super(card);
    }

    @Override
    public GaladrielsDismissal copy() {
        return new GaladrielsDismissal(this);
    }
}

class GaladrielsDismissalPhaseOutTargetPlayerEffect extends OneShotEffect {

    public GaladrielsDismissalPhaseOutTargetPlayerEffect() {
        super(Outcome.Benefit);
        this.staticText = "each creature target player controls phases out. <i>(Treat phased-out creatures and anything attached to them as though they don't exist until their controller's next turn.)</i>";
    }

    private GaladrielsDismissalPhaseOutTargetPlayerEffect(final GaladrielsDismissalPhaseOutTargetPlayerEffect effect) {
        super(effect);
    }

    @Override
    public GaladrielsDismissalPhaseOutTargetPlayerEffect copy() {
        return new GaladrielsDismissalPhaseOutTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            List<UUID> permIds = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURES, player.getId(), game)) {
                permIds.add(permanent.getId());
            }
            return new PhaseOutAllEffect(permIds).apply(game, source);
        }
        return false;
    }
}

enum GaladrielsDismissalAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetPlayer());
        } else {
            ability.addTarget(new TargetCreaturePermanent());
        }
    }
}
