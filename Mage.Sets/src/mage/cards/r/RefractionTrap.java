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
package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class RefractionTrap extends CardImpl {

    public RefractionTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");
        this.subtype.add("Trap");

        // If an opponent cast a red instant or sorcery spell this turn, you may pay {W} rather than pay Refraction Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{W}"), RefractionTrapCondition.getInstance()), new SpellsCastWatcher());

        // Prevent the next 3 damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, Refraction Trap deals that much damage to target creature or player.
        this.getSpellAbility().addEffect(new RefractionTrapPreventDamageEffect(Duration.EndOfTurn, 3));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public RefractionTrap(final RefractionTrap card) {
        super(card);
    }

    @Override
    public RefractionTrap copy() {
        return new RefractionTrap(this);
    }
}

class RefractionTrapCondition implements Condition {

    private static final RefractionTrapCondition fInstance = new RefractionTrapCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getName());
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(opponentId);
                if (spells != null) {
                    for (Spell spell : spells) {
                        if ((spell.getCardType().contains(CardType.SORCERY) || spell.getCardType().contains(CardType.INSTANT))
                                && spell.getColor(game).isRed()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent cast a red instant or sorcery spell this turn";
    }
}

class RefractionTrapPreventDamageEffect extends PreventionEffectImpl {

    private final TargetSource target;
    private final int amount;

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
                player.damage(preventionData.getPreventedDamage(), source.getSourceId(), game, false, true);
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
            if (!object.getId().equals(target.getFirstTarget())
                    && !((object instanceof StackObject) && ((StackObject) object).getSourceId().equals(target.getFirstTarget()))) {
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
