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
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.PreventDamageBySourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public class ImpulsiveManeuvers extends CardImpl {

    public ImpulsiveManeuvers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{R}");

        // Whenever a creature attacks, flip a coin. If you win the flip, the next time that creature would deal combat damage this turn, it deals double that damage instead. If you lose the flip, the next time that creature would deal combat damage this turn, prevent that damage.
        this.addAbility(new AttacksAllTriggeredAbility(new ImpulsiveManeuversEffect(), false, StaticFilters.FILTER_PERMANENT_CREATURE,
                SetTargetPointer.PERMANENT, false));
    }

    public ImpulsiveManeuvers(final ImpulsiveManeuvers card) {
        super(card);
    }

    @Override
    public ImpulsiveManeuvers copy() {
        return new ImpulsiveManeuvers(this);
    }
}

class ImpulsiveManeuversEffect extends PreventionEffectImpl {
    
    private boolean wonFlip;

    public ImpulsiveManeuversEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "flip a coin. If you win the flip, the next time that creature would deal combat damage this turn, it deals double that damage instead. If you lose the flip, the next time that creature would deal combat damage this turn, prevent that damage";
    }

    public ImpulsiveManeuversEffect(final ImpulsiveManeuversEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        this.wonFlip = game.getPlayer(source.getControllerId()).flipCoin(game);
        super.init(source, game);
    }

    @Override
    public ImpulsiveManeuversEffect copy() {
        return new ImpulsiveManeuversEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CREATURE ||
                event.getType() == GameEvent.EventType.DAMAGE_PLANESWALKER ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object == null) {
            game.informPlayers("Couldn't find source of damage");
            return false;
        }
        return event.getSourceId().equals(this.getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && object != null) {
            if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
                DamageEvent damageEvent = (DamageEvent) event;
                if (damageEvent.isCombatDamage()) {
                    if (wonFlip) {
                        event.setAmount(CardUtil.addWithOverflowCheck(event.getAmount(), event.getAmount()));
                        this.discard();
                    } else {
                        preventDamageAction(event, source, game);
                        this.discard();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
