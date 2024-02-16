package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Planes;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Plane;
import mage.players.Player;

import java.util.List;

/**
 * @author Susucr
 */
public class PlaneswalkEffect extends OneShotEffect {

    private final boolean optional;

    public PlaneswalkEffect(boolean optional) {
        super(Outcome.Neutral);
        this.optional = optional;
        staticText = optional ? "you may planeswalk" : "you planeswalk";
    }

    protected PlaneswalkEffect(final PlaneswalkEffect effect) {
        super(effect);
        this.optional = effect.optional;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // As of now, a player may planeswalk iff there are planes in the command zone.
        boolean canPlaneswalk = game.getState().getCommand().stream().anyMatch(obj -> obj instanceof Plane);
        if (!canPlaneswalk) {
            return true; // Not playing with planeswalk enabled.
        }

        if (optional && !controller.chooseUse(outcome, "Planeswalk?", source, game)) {
            return true;
        }

        // Steps: 1) Remove the last plane and set its effects to discarded
        for (CommandObject cobject : game.getState().getCommand()) {
            if (cobject instanceof Plane) {
                if (cobject.getAbilities() != null) {
                    for (Ability ability : cobject.getAbilities()) {
                        for (Effect effect : ability.getEffects()) {
                            if (effect instanceof ContinuousEffect) {
                                ((ContinuousEffect) effect).discard();
                            }
                        }
                    }
                }
                game.getState().removeTriggersOfSourceId(cobject.getId());
                game.getState().getCommand().remove(cobject);
                break;
            }
        }

        // 2) Choose a new random plane we haven't been to, or reset if we've been everywhere
        List<String> planesVisited = game.getState().getSeenPlanes();
        if (game.getState().getSeenPlanes() != null) {
            if (planesVisited.size() == Planes.values().length) {
                game.getState().resetSeenPlanes();
            }
        }

        boolean foundNextPlane = false;
        while (!foundNextPlane) {
            Plane plane = Plane.createRandomPlane();
            try {
                if (plane != null && !planesVisited.contains(plane.getName())) {
                    foundNextPlane = true;
                    game.addPlane(plane, controller.getId());
                }
            } catch (Exception ex) {
            }
        }
        return true;
    }

    @Override
    public PlaneswalkEffect copy() {
        return new PlaneswalkEffect(this);
    }
}
