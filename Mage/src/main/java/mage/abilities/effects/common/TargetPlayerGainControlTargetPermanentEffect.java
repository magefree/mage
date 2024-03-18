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
public class TargetPlayerGainControlTargetPermanentEffect extends OneShotEffect {

    private final String playerDescription;

    public TargetPlayerGainControlTargetPermanentEffect() {
        this("");
    }

    public TargetPlayerGainControlTargetPermanentEffect(String playerDescription) {
        super(Outcome.Benefit);
        this.playerDescription = playerDescription;
    }

    protected TargetPlayerGainControlTargetPermanentEffect(final TargetPlayerGainControlTargetPermanentEffect effect) {
        super(effect);
        this.playerDescription = effect.playerDescription;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() != 2) {
            throw new IllegalStateException("It must have two targets, but found " + source.getTargets().size());
        }
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, player.getId()
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }

    @Override
    public TargetPlayerGainControlTargetPermanentEffect copy() {
        return new TargetPlayerGainControlTargetPermanentEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().size() != 2) {
            throw new IllegalStateException("It must have two targets, but found " + mode.getTargets().size());
        }
        return (playerDescription.isEmpty() ? mode.getTargets().get(0).getDescription() : playerDescription) +
                " gains control of " + mode.getTargets().get(1).getDescription();
    }
}
