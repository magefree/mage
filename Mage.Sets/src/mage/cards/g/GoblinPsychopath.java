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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class GoblinPsychopath extends CardImpl {

    public GoblinPsychopath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Goblin Psychopath attacks or blocks, flip a coin. If you lose the flip, the next time it would deal combat damage this turn, it deals that damage to you instead.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new GoblinPsychopathEffect(), false));
    }

    public GoblinPsychopath(final GoblinPsychopath card) {
        super(card);
    }

    @Override
    public GoblinPsychopath copy() {
        return new GoblinPsychopath(this);
    }
}

class GoblinPsychopathEffect extends ReplacementEffectImpl {
    
    private boolean wonFlip;

    public GoblinPsychopathEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "flip a coin. If you lose the flip, the next time it would deal combat damage this turn, it deals that damage to you instead";
    }

    public GoblinPsychopathEffect(final GoblinPsychopathEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        this.wonFlip = game.getPlayer(source.getControllerId()).flipCoin(game);
        super.init(source, game);
    }

    @Override
    public GoblinPsychopathEffect copy() {
        return new GoblinPsychopathEffect(this);
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
        return event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && object != null) {
            if (this.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
                DamageEvent damageEvent = (DamageEvent) event;
                if (damageEvent.isCombatDamage()) {
                    if (!wonFlip) {
                        // TODO: make this redirect damage from all blockers
                        controller.damage(event.getAmount(), source.getSourceId(), game, false, true);
                        String sourceLogName = source != null ? game.getObject(source.getSourceId()).getLogName() + ": " : "";
                        game.informPlayers(sourceLogName + "Redirected " + event.getAmount() + " damage to " + controller.getLogName());
                        this.discard();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
