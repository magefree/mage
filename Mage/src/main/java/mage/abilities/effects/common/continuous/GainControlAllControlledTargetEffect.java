package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Susucr
 */
public class GainControlAllControlledTargetEffect extends OneShotEffect {

    private final FilterPermanent filter;

    public GainControlAllControlledTargetEffect(FilterPermanent filter) {
        super(Outcome.GainControl);
        this.filter = filter;
    }

    protected GainControlAllControlledTargetEffect(final GainControlAllControlledTargetEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public GainControlAllControlledTargetEffect copy() {
        return new GainControlAllControlledTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        FilterPermanent filterForPlayer = this.filter.copy();
        filterForPlayer.add(new ControllerIdPredicate(player.getId()));
        return new GainControlAllEffect(Duration.EndOfGame, filterForPlayer, source.getControllerId())
                .apply(game, source);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String text = "gain control of all " + filter.getMessage() + " ";
        text += getTargetPointer().describeTargets(mode.getTargets(), "target player");
        text += " controls";
        return text;
    }
}
