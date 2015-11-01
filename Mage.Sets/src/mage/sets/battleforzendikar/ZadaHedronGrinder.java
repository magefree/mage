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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class ZadaHedronGrinder extends CardImpl {

    public ZadaHedronGrinder(UUID ownerId) {
        super(ownerId, 162, "Zada, Hedron Grinder", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "BFZ";
        this.supertype.add("Legendary");
        this.subtype.add("Goblin");
        this.subtype.add("Ally");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        this.addAbility(new ZadaHedronGrinderTriggeredAbility());

    }

    public ZadaHedronGrinder(final ZadaHedronGrinder card) {
        super(card);
    }

    @Override
    public ZadaHedronGrinder copy() {
        return new ZadaHedronGrinder(this);
    }
}

class ZadaHedronGrinderTriggeredAbility extends TriggeredAbilityImpl {

    ZadaHedronGrinderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ZadaHedronGrinderEffect(), false);
    }

    ZadaHedronGrinderTriggeredAbility(final ZadaHedronGrinderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZadaHedronGrinderTriggeredAbility copy() {
        return new ZadaHedronGrinderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (isControlledInstantOrSorcery(spell)) {
                boolean targetsSource = false;
                for (UUID modeId : spell.getSpellAbility().getModes().getSelectedModes()) {
                    spell.getSpellAbility().getModes().setActiveMode(modeId);
                    for (Target target : spell.getSpellAbility().getTargets()) {
                        for (UUID targetId : target.getTargets()) {
                            if (targetId.equals(getSourceId())) {
                                targetsSource = true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                if (targetsSource) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(spell.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isControlledInstantOrSorcery(Spell spell) {
        return spell != null
                && (spell.getControllerId().equals(this.getControllerId()))
                && (spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY));
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell that targets only {this}, copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures.";
    }
}

class ZadaHedronGrinderEffect extends OneShotEffect {

    public ZadaHedronGrinderEffect() {
        super(Outcome.Detriment);
        this.staticText = "copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures";
    }

    public ZadaHedronGrinderEffect(final ZadaHedronGrinderEffect effect) {
        super(effect);
    }

    @Override
    public ZadaHedronGrinderEffect copy() {
        return new ZadaHedronGrinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell == null) {
            spell = (Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK);
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (spell != null && controller != null) {
            Target usedTarget = null;
            setUsedTarget:
            for (UUID modeId : spell.getSpellAbility().getModes().getSelectedModes()) {
                spell.getSpellAbility().getModes().setActiveMode(modeId);
                for (Target target : spell.getSpellAbility().getTargets()) {
                    if (target.getFirstTarget().equals(source.getSourceId())) {
                        usedTarget = target.copy();
                        usedTarget.clearChosen();
                        break setUsedTarget;
                    }
                }
            }
            if (usedTarget == null) {
                return false;
            }
            for (Permanent creature : game.getState().getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
                if (!creature.getId().equals(source.getSourceId()) && usedTarget.canTarget(source.getControllerId(), creature.getId(), source, game)) {
                    Spell copy = spell.copySpell();
                    setTarget:
                    for (UUID modeId : spell.getSpellAbility().getModes().getSelectedModes()) {
                        copy.getSpellAbility().getModes().setActiveMode(modeId);
                        for (Target target : copy.getSpellAbility().getTargets()) {
                            if (target.getClass().equals(usedTarget.getClass()) && target.getMessage().equals(usedTarget.getMessage())) {
                                target.clearChosen();
                                target.add(creature.getId(), game);
                                break setTarget;
                            }
                        }
                    }
                    copy.setControllerId(source.getControllerId());
                    copy.setCopiedSpell(true);
                    game.getStack().push(copy);
                    String activateMessage = copy.getActivatedMessage(game);
                    if (activateMessage.startsWith(" casts ")) {
                        activateMessage = activateMessage.substring(6);
                    }
                    if (!game.isSimulation()) {
                        game.informPlayers(controller.getLogName() + activateMessage);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
