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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class UginsNexus extends CardImpl {

    public UginsNexus(UUID ownerId) {
        super(ownerId, 227, "Ugin's Nexus", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "KTK";
        this.supertype.add("Legendary");

        // If a player would begin an extra turn, that player skips that turn instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UginsNexusSkipExtraTurnsEffect()));
        
        // If Ugin's Nexus would be put into a graveyard from the battlefield, instead exile it and take an extra turn after this one.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UginsNexusExileEffect()));
    }

    public UginsNexus(final UginsNexus card) {
        super(card);
    }

    @Override
    public UginsNexus copy() {
        return new UginsNexus(this);
    }
}

class UginsNexusSkipExtraTurnsEffect extends ReplacementEffectImpl {

    public UginsNexusSkipExtraTurnsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If a player would begin an extra turn, that player skips that turn instead";
    }

    public UginsNexusSkipExtraTurnsEffect(final UginsNexusSkipExtraTurnsEffect effect) {
        super(effect);
    }

    @Override
    public UginsNexusSkipExtraTurnsEffect copy() {
        return new UginsNexusSkipExtraTurnsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && sourceObject != null) {
            game.informPlayers(sourceObject.getLogName() + ": Extra turn of " + player.getLogName() + " skipped");
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == EventType.EXTRA_TURN;
    }

}

class UginsNexusExileEffect extends ReplacementEffectImpl {

    public UginsNexusExileEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If {this} would be put into a graveyard from the battlefield, instead exile it and take an extra turn after this one";
    }

    public UginsNexusExileEffect(final UginsNexusExileEffect effect) {
        super(effect);
    }

    @Override
    public UginsNexusExileEffect copy() {
        return new UginsNexusExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent)event).getTarget();
        if (permanent != null) {
            permanent.moveToExile(null, "", source.getSourceId(), game);
            new AddExtraTurnControllerEffect().apply(game, source);
            return true;
        }
        return false;
    }
    
    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getToZone() == Zone.GRAVEYARD && zEvent.getFromZone().equals(Zone.BATTLEFIELD)) {
                return true;
            }
        }
        return false;
    }

}
