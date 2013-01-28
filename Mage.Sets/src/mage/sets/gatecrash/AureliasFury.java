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
package mage.sets.gatecrash;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayerAmount;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.WatcherImpl;

/**
 * GATECRASH FAQ 11.01.2013
 *
 * You announce the value of X and how the damage will be divided as part of
 * casting Aurelia's Fury. Each chosen target must receive at least 1 damage.
 *
 * Aurelia's Fury can't deal damage to both a planeswalker and that
 * planeswalker's controller. If damage dealt by Aurelia's Fury is redirected
 * from a player to a planeswalker he or she controls, that player will be able
 * to cast noncreature spells that turn. If you want to stop a player from
 * casting noncreature spells this turn, you can't choose to redirect the
 * damage to a planeswalker he or she controls.
 *
 * If Aurelia's Fury has multiple targets, and some but not all of them are
 * illegal targets when Aurelia's Fury resolves, Aurelia's Fury will still
 * deal damage to the remaining legal targets according to the original damage
 * division.
 *
 * If all of the targets are illegal when Aurelia's Fury tries to resolve,
 * it will be countered and none of its effects will happen. No creature or
 * player will be dealt damage.
 *
 * @author LevelX2
 */
public class AureliasFury extends CardImpl<AureliasFury> {

    public AureliasFury(UUID ownerId) {
        super(ownerId, 144, "Aurelia's Fury", Rarity.MYTHIC, new CardType[]{CardType.INSTANT}, "{X}{R}{W}");
        this.expansionSetCode = "GTC";

        this.color.setRed(true);
        this.color.setWhite(true);

        // Aurelia's Fury deals X damage divided as you choose among any number of target creatures and/or players.
        // Tap each creature dealt damage this way. Players dealt damage this way can't cast noncreature spells this turn.
        DynamicValue xValue = new ManacostVariableValue();
        this.getSpellAbility().addEffect(new DamageMultiEffect(xValue));
        this.getSpellAbility().addEffect(new AureliasFuryEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayerAmount(xValue));
        this.addWatcher(new AureliasFuryDamagedByWatcher());

    }

    public AureliasFury(final AureliasFury card) {
        super(card);
    }

    @Override
    public AureliasFury copy() {
        return new AureliasFury(this);
    }
}

class AureliasFuryEffect extends OneShotEffect<AureliasFuryEffect> {
    
    public AureliasFuryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Tap each creature dealt damage this way. Players dealt damage this way can't cast noncreature spells this turn";
    }
    
    public AureliasFuryEffect(final AureliasFuryEffect effect) {
        super(effect);
    }
    
    @Override
    public AureliasFuryEffect copy() {
        return new AureliasFuryEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        AureliasFuryDamagedByWatcher watcher = (AureliasFuryDamagedByWatcher) game.getState().getWatchers().get("AureliasFuryDamagedByWatcher", source.getSourceId());
        if (watcher != null) {
            for(UUID creatureId : watcher.damagedCreatures) {
                Permanent permanent = game.getPermanent(creatureId);
                if (permanent != null) {
                    permanent.tap(game);
                }
            }
            for(UUID playerId : watcher.damagedPlayers) {
                ContinuousEffect effect = new AureliasFuryCantCastEffect();
                effect.setTargetPointer(new FixedTarget(playerId));
                game.addEffect(effect, source);
            }
            watcher.reset();
        }
                
        return false;
    }
}

class AureliasFuryCantCastEffect extends ReplacementEffectImpl<AureliasFuryCantCastEffect> {

    public AureliasFuryCantCastEffect() {
        super(Constants.Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Players dealt damage this way can't cast noncreature spells this turn";
    }

    public AureliasFuryCantCastEffect(final AureliasFuryCantCastEffect effect) {
        super(effect);
    }

    @Override
    public AureliasFuryCantCastEffect copy() {
        return new AureliasFuryCantCastEffect(this);
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
        if (event.getType() == GameEvent.EventType.CAST_SPELL ) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null && player.getId().equals(event.getPlayerId())) {
                Card card = game.getCard(event.getSourceId());
                if (card != null && !card.getCardType().contains(CardType.CREATURE)) {
                    return true;
                }
            }
        }
        return false;
    }
}

class AureliasFuryDamagedByWatcher extends WatcherImpl<AureliasFuryDamagedByWatcher> {

    public List<UUID> damagedCreatures = new ArrayList<UUID>();
    public List<UUID> damagedPlayers = new ArrayList<UUID>();

    public AureliasFuryDamagedByWatcher() {
        super("AureliasFuryDamagedByWatcher", WatcherScope.CARD);
    }

    public AureliasFuryDamagedByWatcher(final AureliasFuryDamagedByWatcher watcher) {
        super(watcher);
        this.damagedCreatures = watcher.damagedCreatures;
        this.damagedPlayers = watcher.damagedPlayers;
    }

    @Override
    public AureliasFuryDamagedByWatcher copy() {
        return new AureliasFuryDamagedByWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_CREATURE) {
            MageObject obj = game.getObject(event.getSourceId());
            if (obj instanceof Spell) {
                if (sourceId.equals(((Spell) obj).getSourceId()) && !damagedCreatures.contains(event.getTargetId())) {
                    damagedCreatures.add(event.getTargetId());
                }
            }
        }
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            MageObject obj = game.getObject(event.getSourceId());
            if (obj instanceof Spell) {
                if (sourceId.equals(((Spell) obj).getSourceId()) && !damagedPlayers.contains(event.getTargetId())) {
                    damagedPlayers.add(event.getTargetId());
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagedCreatures.clear();
        damagedPlayers.clear();
    }

}
