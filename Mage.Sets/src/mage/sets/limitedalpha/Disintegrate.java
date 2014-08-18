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
package mage.sets.limitedalpha;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author dustinconrad
 */
public class Disintegrate extends CardImpl {

    public Disintegrate(UUID ownerId) {
        super(ownerId, 141, "Disintegrate", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{X}{R}");
        this.expansionSetCode = "LEA";

        this.color.setRed(true);

        // Disintegrate deals X damage to target creature or player. That creature can't be regenerated this turn. If the creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        this.getSpellAbility().addEffect(new DisintegrateCantRegenerateEffect());
        this.getSpellAbility().addEffect(new DisintegrateExileEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public Disintegrate(final Disintegrate card) {
        super(card);
    }

    @Override
    public Disintegrate copy() {
        return new Disintegrate(this);
    }
}

class DisintegrateCantRegenerateEffect extends ReplacementEffectImpl {

    public DisintegrateCantRegenerateEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "That creature can't be regenerated this turn";
    }

    public DisintegrateCantRegenerateEffect(final DisintegrateCantRegenerateEffect effect) {
        super(effect);
    }

    @Override
    public DisintegrateCantRegenerateEffect copy() {
        return new DisintegrateCantRegenerateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (GameEvent.EventType.REGENERATE.equals(event.getType())) {
            UUID targetId = getTargetPointer().getFirst(game, source);
            if (targetId != null) {
                return targetId.equals(event.getTargetId());
            }
        }
        return false;
    }
}

class DisintegrateExileEffect extends ReplacementEffectImpl {

    public DisintegrateExileEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If the creature would die this turn, exile it instead";
    }

    public DisintegrateExileEffect(final DisintegrateExileEffect effect) {
        super(effect);
    }

    @Override
    public DisintegrateExileEffect copy() {
        return new DisintegrateExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (permanent != null) {
            return permanent.moveToExile(null, "", source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (GameEvent.EventType.ZONE_CHANGE.equals(event.getType()) && ((ZoneChangeEvent) event).isDiesEvent()) {
            UUID targetId = getTargetPointer().getFirst(game, source);
            if (targetId != null) {
                return targetId.equals(event.getTargetId());
            }
        }
        return false;
    }
}