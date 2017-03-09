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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetStackObject;
import mage.target.Targets;

/**
 *
 * @author BetaSteward
 */
public class Spellskite extends CardImpl {

    public Spellskite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add("Horror");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {U/P}: Change a target of target spell or ability to Spellskite.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SpellskiteEffect(), new ManaCostsImpl("{U/P}"));
        ability.addTarget(new TargetStackObject());
        this.addAbility(ability);
    }

    public Spellskite(final Spellskite card) {
        super(card);
    }

    @Override
    public Spellskite copy() {
        return new Spellskite(this);
    }
}

class SpellskiteEffect extends OneShotEffect {

    public SpellskiteEffect() {
        super(Outcome.Neutral);
        staticText = "Change a target of target spell or ability to {this}";
    }

    public SpellskiteEffect(final SpellskiteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (stackObject != null && sourceObject != null) {
            Targets targets = new Targets();
            Ability sourceAbility;
            String oldTargetName = null;
            if (stackObject instanceof Spell) {
                Spell spell = (Spell) stackObject;
                sourceAbility = spell.getSpellAbility();
            } else if (stackObject instanceof StackAbility) {
                StackAbility stackAbility = (StackAbility) stackObject;
                sourceAbility = stackAbility;
            } else {
                return false;
            }
            for (UUID modeId : sourceAbility.getModes().getSelectedModes()) {
                Mode mode = sourceAbility.getModes().get(modeId);
                targets.addAll(mode.getTargets());
            }

            boolean twoTimesTarget = false;
            if (targets.size() == 1 && targets.get(0).getTargets().size() == 1) {
                Target target = targets.get(0);
                if (target.getFirstTarget().equals(source.getSourceId())) {
                    return true; // Target was already the same source, so no change / target event to create
                }
                if (target.canTarget(stackObject.getControllerId(), source.getSourceId(), sourceAbility, game)) {
                    oldTargetName = getTargetName(targets.getFirstTarget(), game);
                    target.clearChosen();
                    // The source is still the spell on the stack
                    target.addTarget(source.getSourceId(), stackObject.getStackAbility(), game);
                }
            } else { //needed for targeted source's with multiple targets
                Player controller = game.getPlayer(source.getControllerId());
                boolean validTargets = false;
                do {
                    for (Target target : targets) {
                        for (UUID targetId : target.getTargets()) {
                            String name = getTargetName(targetId, game);
                            if (targetId.equals(source.getSourceId())
                                    || target.getTargets().contains(source.getSourceId())) {
                                // you can't change this target to source because the source is already another targetId of that target.
                                twoTimesTarget = true;
                                continue;
                            }
                            if (target.canTarget(stackObject.getControllerId(), source.getSourceId(), sourceAbility, game)
                                    && !twoTimesTarget) {
                                validTargets = true;
                                if (name != null
                                        && controller.chooseUse(Outcome.Neutral, "Change target from " + name + " to " + sourceObject.getLogName() + '?', source, game)) {
                                    oldTargetName = getTargetName(targetId, game);
                                    int damageAmount = target.getTargetAmount(targetId);
                                    target.remove(targetId);
                                    // The source is still the spell on the stack
                                    // add the source permanent id and set the damage if any
                                    target.addTarget(source.getSourceId(), stackObject.getStackAbility(), game);
                                    target.setTargetAmount(source.getSourceId(), damageAmount, game);
                                    break;
                                }
                            }
                        }
                        if (oldTargetName != null) {
                            break;
                        }
                    }
                    if (oldTargetName == null) {
                        game.informPlayer(controller, "You have to select at least one target to change to " + sourceObject.getIdName() + '!');
                    }
                } while (validTargets && oldTargetName == null);
            }
            if (oldTargetName != null) {
                game.informPlayers(sourceObject.getLogName() + ": Changed target of " + stackObject.getLogName() + " from " + oldTargetName + " to " + sourceObject.getLogName());
            } else if (twoTimesTarget) {
                game.informPlayers(sourceObject.getLogName() + ": Target not changed to " + sourceObject.getLogName() + " because its not valid to target it twice for " + stackObject.getLogName());
            } else {
                game.informPlayers(sourceObject.getLogName() + ": Target not changed to " + sourceObject.getLogName() + " because its no valid target for " + stackObject.getLogName());
            }
            return true;
        }
        return false;
    }

    @Override
    public SpellskiteEffect copy() {
        return new SpellskiteEffect(this);
    }

    private String getTargetName(UUID objectId, Game game) {
        MageObject object = game.getObject(objectId);
        if (object != null) {
            return object.getLogName();
        }
        Player player = game.getPlayer(objectId);
        if (player != null) {
            return player.getLogName();
        }
        return null;
    }
}
