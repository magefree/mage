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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
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
package mage.game.command.emblems;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author spjspj
 */
public class DackFaydenEmblem extends Emblem {

    public DackFaydenEmblem() {
        this.setName("Emblem Dack");
        this.getAbilities().add(new DackFaydenEmblemTriggeredAbility());
    }
}

class DackFaydenEmblemTriggeredAbility extends TriggeredAbilityImpl {

    DackFaydenEmblemTriggeredAbility() {
        super(Zone.COMMAND, new DackFaydenEmblemEffect(), false);
    }

    DackFaydenEmblemTriggeredAbility(final DackFaydenEmblemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DackFaydenEmblemTriggeredAbility copy() {
        return new DackFaydenEmblemTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean returnValue = false;
        List<UUID> targetedPermanentIds = new ArrayList<>(0);
        Player player = game.getPlayer(this.getControllerId());
        if (player != null) {
            if (event.getPlayerId().equals(this.getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null) {
                    SpellAbility spellAbility = spell.getSpellAbility();
                    for (Target target : spellAbility.getTargets()) {
                        if (!target.isNotTarget()) {
                            for (UUID targetId : target.getTargets()) {
                                if (game.getBattlefield().containsPermanent(targetId)) {
                                    returnValue = true;
                                    targetedPermanentIds.add(targetId);
                                }
                            }
                        }
                    }
                    for (Effect effect : spellAbility.getEffects()) {
                        for (UUID targetId : effect.getTargetPointer().getTargets(game, spellAbility)) {
                            if (game.getBattlefield().containsPermanent(targetId)) {
                                returnValue = true;
                                targetedPermanentIds.add(targetId);
                            }
                        }
                    }
                }
            }
        }
        for (Effect effect : this.getEffects()) {
            if (effect instanceof DackFaydenEmblemEffect) {
                DackFaydenEmblemEffect dackEffect = (DackFaydenEmblemEffect) effect;
                List<Permanent> permanents = new ArrayList<>();
                for (UUID permanentId : targetedPermanentIds) {
                    Permanent permanent = game.getPermanent(permanentId);
                    if (permanent != null) {
                        permanents.add(permanent);
                    }
                }

                dackEffect.setTargets(permanents, game);
            }
        }
        return returnValue;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell that targets one or more permanents, gain control of those permanents.";
    }
}

class DackFaydenEmblemEffect extends ContinuousEffectImpl {

    protected FixedTargets fixedTargets;

    DackFaydenEmblemEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "gain control of those permanents";
    }

    DackFaydenEmblemEffect(final DackFaydenEmblemEffect effect) {
        super(effect);
        this.fixedTargets = effect.fixedTargets;
    }

    @Override
    public DackFaydenEmblemEffect copy() {
        return new DackFaydenEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : fixedTargets.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                permanent.changeControllerId(source.getControllerId(), game);
            }
        }
        return true;
    }

    public void setTargets(List<Permanent> targetedPermanents, Game game) {
        this.fixedTargets = new FixedTargets(targetedPermanents, game);
    }
}
