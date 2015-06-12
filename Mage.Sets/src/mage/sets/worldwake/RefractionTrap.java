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
package mage.sets.worldwake;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class RefractionTrap extends CardImpl {

    public RefractionTrap(UUID ownerId) {
        super(ownerId, 17, "Refraction Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Trap");


        // If an opponent cast a red instant or sorcery spell this turn, you may pay {W} rather than pay Refraction Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new RefractionTrapAlternativeCost());

        // Prevent the next 3 damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, Refraction Trap deals that much damage to target creature or player.
        this.getSpellAbility().addEffect(new RefractionTrapPreventDamageEffect(Duration.EndOfTurn, 3));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());

        this.getSpellAbility().addWatcher(new RefractionTrapWatcher());
    }

    public RefractionTrap(final RefractionTrap card) {
        super(card);
    }

    @Override
    public RefractionTrap copy() {
        return new RefractionTrap(this);
    }
}

class RefractionTrapWatcher extends Watcher {

    Set<UUID> playersMetCondition = new HashSet<>();
            
    public RefractionTrapWatcher() {
        super("RefractionTrapWatcher", WatcherScope.GAME);
    }

    public RefractionTrapWatcher(final RefractionTrapWatcher watcher) {
        super(watcher);
        this.playersMetCondition.addAll(watcher.playersMetCondition);
    }

    @Override
    public RefractionTrapWatcher copy() {
        return new RefractionTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell.getColor(game).isRed()) {
                if (spell.getCardType().contains(CardType.INSTANT)
                        || spell.getCardType().contains(CardType.SORCERY)) {
                    playersMetCondition.add(event.getPlayerId());
                }
            }
        }
    }

    public boolean conditionMetForAnOpponent(UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            for(UUID playerId: playersMetCondition) {
                if (controller.hasOpponent(playerId, game)) {
                    return true;
                }
            }
        }
        return false;
        
    }
    @Override
    public void reset() {
        playersMetCondition.clear();
        super.reset();        
    }
}

class RefractionTrapAlternativeCost extends AlternativeCostImpl {

    public RefractionTrapAlternativeCost() {
        super("You may pay {W} rather than pay Refraction Trap's mana cost");
        this.add(new ManaCostsImpl<ManaCost>("{W}"));
    }

    public RefractionTrapAlternativeCost(final RefractionTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public RefractionTrapAlternativeCost copy() {
        return new RefractionTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        RefractionTrapWatcher watcher = (RefractionTrapWatcher) game.getState().getWatchers().get("RefractionTrapWatcher");
        return watcher != null && watcher.conditionMetForAnOpponent(source.getControllerId(), game);
    }

    @Override
    public String getText() {
        return "If an opponent cast a red instant or sorcery spell this turn, you may pay {W} rather than pay {this} mana cost";
    }
}

class RefractionTrapPreventDamageEffect extends PreventionEffectImpl {

    private final TargetSource target;
    private int amount;

    public RefractionTrapPreventDamageEffect(Duration duration, int amount) {
        super(duration, amount, false, false);
        this.amount = amount;
        this.target = new TargetSource();
        staticText = "The next " + amount + " damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, {this} deals that much damage to target creature or player";
    }

    public RefractionTrapPreventDamageEffect(final RefractionTrapPreventDamageEffect effect) {
        super(effect);
        this.amount = effect.amount;
         this.target = effect.target.copy();
    }

    @Override
    public RefractionTrapPreventDamageEffect copy() {
        return new RefractionTrapPreventDamageEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
        super.init(source, game);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        this.used = true;
        this.discard(); // only one use
        if (preventionData.getPreventedDamage() > 0) {
            UUID damageTarget = getTargetPointer().getFirst(game, source);
            Permanent permanent = game.getPermanent(damageTarget);
            if (permanent != null) {
                game.informPlayers("Dealing " + preventionData.getPreventedDamage() + " to " + permanent.getLogName());
                permanent.damage(preventionData.getPreventedDamage(), source.getSourceId(), game, false, true);
            }
            Player player = game.getPlayer(damageTarget);
            if (player != null) {
                game.informPlayers("Dealing " + preventionData.getPreventedDamage() + " to " + player.getLogName());
                player.damage(preventionData.getPreventedDamage(), source.getSourceId(), game, true, false);
            }
        }

        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            // check source
            MageObject object = game.getObject(event.getSourceId());
            if (object == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }

            // check damage source
            if (!object.getId().equals(target.getFirstTarget()) && 
                    !((object instanceof StackObject) && ((StackObject)object).getSourceId().equals(target.getFirstTarget()))) {
                return false;
            }

            // check target
            //   check permanent first
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (permanent.getControllerId().equals(source.getControllerId())) {
                    // it's your permanent
                    return true;
                }
            }
            //   check player
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                if (player.getId().equals(source.getControllerId())) {
                    // it is you
                    return true;
                }
            }
        }
        return false;
    }
}
