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
package mage.sets.tempest;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class SoltariGuerrillas extends CardImpl {

    public SoltariGuerrillas(UUID ownerId) {
        super(ownerId, 347, "Soltari Guerrillas", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        this.expansionSetCode = "TMP";
        this.subtype.add("Soltari");
        this.subtype.add("Soldier");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // {0}: The next time Soltari Guerrillas would deal combat damage to an opponent this turn, it deals that damage to target creature instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoltariGuerrillasReplacementEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public SoltariGuerrillas(final SoltariGuerrillas card) {
        super(card);
    }

    @Override
    public SoltariGuerrillas copy() {
        return new SoltariGuerrillas(this);
    }
}

class SoltariGuerrillasReplacementEffect extends PreventionEffectImpl {

    SoltariGuerrillasReplacementEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true, false);
        staticText = "The next time {this} would deal combat damage to an opponent this turn, it deals that damage to target creature instead";
    }

    SoltariGuerrillasReplacementEffect(final SoltariGuerrillasReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.DAMAGED_PLAYER);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            Player controller = game.getPlayer(source.getControllerId());
            return controller.hasOpponent(event.getTargetId(), game);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionResult = preventDamageAction(event, source, game);
        if (preventionResult.getPreventedDamage() > 0) {
            Permanent redirectTo = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (redirectTo != null) {
                game.informPlayers("Dealing " + preventionResult.getPreventedDamage() + " to " + redirectTo.getLogName() + " instead.");
                DamageEvent damageEvent = (DamageEvent) event;
                redirectTo.damage(preventionResult.getPreventedDamage(), event.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
            }
            discard(); // (only once)
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SoltariGuerrillasReplacementEffect copy() {
        return new SoltariGuerrillasReplacementEffect(this);
    }
}