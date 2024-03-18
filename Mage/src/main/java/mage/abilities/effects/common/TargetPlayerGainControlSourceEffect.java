package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author xenohedron
 */
public class TargetPlayerGainControlSourceEffect extends OneShotEffect {

    private final String playerDescription;

    public TargetPlayerGainControlSourceEffect() {
        this("");
    }

    public TargetPlayerGainControlSourceEffect(String playerDescription) {
        super(Outcome.Benefit);
        this.playerDescription = playerDescription;
    }

    protected TargetPlayerGainControlSourceEffect(final TargetPlayerGainControlSourceEffect effect) {
        super(effect);
        this.playerDescription = effect.playerDescription;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, player.getId()
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }

    @Override
    public TargetPlayerGainControlSourceEffect copy() {
        return new TargetPlayerGainControlSourceEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return (playerDescription.isEmpty() ? getTargetPointer().describeTargets(mode.getTargets(), "that player") : playerDescription) +
                " gains control of {this}";
    }
}
