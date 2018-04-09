/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.PlanarDieRoll;
import mage.constants.Planes;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Plane;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class RollPlanarDieEffect extends OneShotEffect {

    private static final Logger log = Logger.getLogger("Roll Planar Die");

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
            PlanarDieRoll planarRoll = controller.rollPlanarDie(game);
            if (planarRoll == PlanarDieRoll.CHAOS_ROLL && chaosEffects != null && chaosTargets != null) {
                for (int i = 0; i < chaosTargets.size(); i++) {
                    Target target = chaosTargets.get(i);
                    if (target != null) {
                        target.clearChosen();
                    }
                }

                for (int i = 0; i < chaosEffects.size(); i++) {
                    Effect effect = chaosEffects.get(i);
                    Target target = null;
                    if (chaosTargets != null && chaosTargets.size() > i) {
                        target = chaosTargets.get(i);
                    }
                    boolean done = false;
                    while (controller.canRespond() && effect != null && !done) {
                        if (target != null && !target.isChosen() && target.canChoose(controller.getId(), game)) {
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
            } else if (planarRoll == PlanarDieRoll.PLANAR_ROLL) {
                // Steps: 1) Remove the last plane and set its effects to discarded
                for (CommandObject cobject : game.getState().getCommand()) {
                    if (cobject instanceof Plane) {
                        game.getState().addSeenPlane((Plane) cobject, game, id);
                        if (((Plane) cobject).getAbilities() != null) {
                            for (Ability ability : ((Plane) cobject).getAbilities()) {
                                for (Effect effect : ability.getEffects()) {
                                    if (effect instanceof ContinuousEffect) {
                                        ((ContinuousEffect) effect).discard();
                                    }
                                }
                            }
                        }
                        game.getState().removeTriggersOfSourceId(((Plane) cobject).getId());
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
                    Plane plane = Plane.getRandomPlane();
                    try {
                        if (plane != null && !planesVisited.contains(plane.getName())) {
                            foundNextPlane = true;
                            plane.setControllerId(controller.getId());
                            game.addPlane(plane, null, controller.getId());
                            game.informPlayers("You have planeswalked to " + plane.getLogName());
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
        StringBuilder sb = new StringBuilder("Roll the planar die");
        return sb.toString();
    }

    @Override
    public RollPlanarDieEffect copy() {
        return new RollPlanarDieEffect(this);
    }
}
