package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.PlanarDieRollResult;
import mage.constants.Planes;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Plane;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author spjspj
 */
public class RollPlanarDieEffect extends OneShotEffect {

    protected List<Effect> chaosEffects = null;
    protected List<Target> chaosTargets = null;

    public RollPlanarDieEffect(List<Effect> chaosEffects, List<Target> chaosTargets) {
        this(chaosEffects, chaosTargets, Outcome.Neutral);
    }

    public RollPlanarDieEffect(List<Effect> chaosEffects, List<Target> chaosTargets, Outcome outcome) {
        super(outcome);
        addChaosEffects(chaosEffects);
        addChaosTargets(chaosTargets);
    }

    public RollPlanarDieEffect(final RollPlanarDieEffect effect) {
        super(effect);
        this.chaosEffects = effect.chaosEffects.stream().collect(Collectors.toList());
        this.chaosTargets = effect.chaosTargets.stream().collect(Collectors.toList());
    }

    public void addChaosEffects(List<Effect> chaosEffects) {
        if (chaosEffects != null) {
            this.chaosEffects = chaosEffects;
        }
    }

    public void addChaosTargets(List<Target> chaosTargets) {
        if (chaosTargets != null) {
            this.chaosTargets = chaosTargets;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null && mageObject != null) {
            PlanarDieRollResult planarRoll = controller.rollPlanarDie(outcome, source, game);
            if (planarRoll == PlanarDieRollResult.CHAOS_ROLL && chaosEffects != null && chaosTargets != null) {
                for (int i = 0; i < chaosTargets.size(); i++) {
                    Target target = chaosTargets.get(i);
                    if (target != null) {
                        target.clearChosen();
                    }
                }

                for (int i = 0; i < chaosEffects.size(); i++) {
                    Effect effect = chaosEffects.get(i);
                    Target target = null;
                    if (chaosTargets.size() > i) {
                        target = chaosTargets.get(i);
                    }
                    boolean done = false;
                    while (controller.canRespond() && effect != null && !done) {
                        if (target != null && !target.isChosen() && target.canChoose(source.getSourceId(), controller.getId(), game)) {
                            controller.chooseTarget(Outcome.Benefit, target, source, game);
                            source.addTarget(target);
                        }
                        if (target != null) {
                            effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                        }
                        try {
                            effect.apply(game, source);
                        } catch (UnsupportedOperationException exception) {
                        }
                        if (effect instanceof ContinuousEffect) {
                            game.addEffect((ContinuousEffect) effect, source);
                        }
                        done = true;
                    }
                }
            } else if (planarRoll == PlanarDieRollResult.PLANAR_ROLL) {
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
                            plane.setControllerId(controller.getId());
                            game.addPlane(plane, null, controller.getId());
                        }
                    } catch (Exception ex) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("Roll the planar die. If you roll CHAOS, ");
        for (int i = 0; i < chaosEffects.size(); i++) {
            Effect effect = chaosEffects.get(i);
            if (effect != null) {
                try {
                    String emode = effect.getText(mode);
                    emode = emode.substring(0, 1).toLowerCase(Locale.ENGLISH) + emode.substring(1);
                    sb.append(emode);
                } catch (Exception e) {
                    sb.append("perform the CHAOS action");
                }
            }
        }
        sb.append(". If you roll PW, planeswalk to a new plane");
        return sb.toString();
    }

    @Override
    public RollPlanarDieEffect copy() {
        return new RollPlanarDieEffect(this);
    }
}
