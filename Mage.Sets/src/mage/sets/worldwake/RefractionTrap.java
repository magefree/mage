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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class RefractionTrap extends CardImpl<RefractionTrap> {

    public RefractionTrap(UUID ownerId) {
        super(ownerId, 17, "Refraction Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Trap");

        this.color.setWhite(true);

        // If an opponent cast a red instant or sorcery spell this turn, you may pay {W} rather than pay Refraction Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new RefractionTrapAlternativeCost());

        // Prevent the next 3 damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, Refraction Trap deals that much damage to target creature or player.
        this.getSpellAbility().addEffect(new RefractionTrapPreventDamageEffect(Constants.Duration.EndOfTurn, 3));
        this.getSpellAbility().addTarget(new TargetSource());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());

        this.addWatcher(new RefractionTrapWatcher());
    }

    public RefractionTrap(final RefractionTrap card) {
        super(card);
    }

    @Override
    public RefractionTrap copy() {
        return new RefractionTrap(this);
    }
}

class RefractionTrapWatcher extends WatcherImpl<RefractionTrapWatcher> {

    public RefractionTrapWatcher() {
        super("RefractionTrapWatcher", Constants.WatcherScope.GAME);
    }

    public RefractionTrapWatcher(final RefractionTrapWatcher watcher) {
        super(watcher);
    }

    @Override
    public RefractionTrapWatcher copy() {
        return new RefractionTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) //no need to check - condition has already occured
        {
            return;
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell.getColor().isRed()) {
                if (spell.getCardType().contains(CardType.INSTANT)
                        || spell.getCardType().contains(CardType.SORCERY)) {
                    condition = true;
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        condition = false;
    }
}

class RefractionTrapAlternativeCost extends AlternativeCostImpl<RefractionTrapAlternativeCost> {

    public RefractionTrapAlternativeCost() {
        super("You may pay {W} rather than pay Refraction Trap's mana cost");
        this.add(new ColoredManaCost(Constants.ColoredManaSymbol.W));
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
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If an opponent cast a red instant or sorcery spell this turn, you may pay {W} rather than pay {this} mana cost";
    }
}

class RefractionTrapPreventDamageEffect extends PreventionEffectImpl<RefractionTrapPreventDamageEffect> {

    private int amount;

    public RefractionTrapPreventDamageEffect(Constants.Duration duration, int amount) {
        super(duration);
        this.amount = amount;
        staticText = "The next " + amount + " damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, {this} deals that much damage to target creature or player";
    }

    public RefractionTrapPreventDamageEffect(final RefractionTrapPreventDamageEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public RefractionTrapPreventDamageEffect copy() {
        return new RefractionTrapPreventDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int prevented = 0;
            if (event.getAmount() >= this.amount) {
                int damage = amount;
                event.setAmount(event.getAmount() - amount);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
                prevented = damage;
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                amount -= damage;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
                prevented = damage;
            }

            // deal damage now
            if (prevented > 0) {
                UUID damageTarget = source.getTargets().get(1).getFirstTarget();
                Permanent target = game.getPermanent(damageTarget);
                if (target != null) {
                    game.informPlayers("Dealing " + prevented + " to " + target.getName());
                    target.damage(prevented, source.getSourceId(), game, true, false);
                }
                Player player = game.getPlayer(damageTarget);
                if (player != null) {
                    game.informPlayers("Dealing " + prevented + " to " + player.getName());
                    player.damage(prevented, source.getSourceId(), game, true, false);
                }
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

            if (!object.getId().equals(source.getFirstTarget())) {
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
