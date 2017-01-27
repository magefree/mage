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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class BattletideAlchemist extends CardImpl {

    public BattletideAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add("Kithkin");
        this.subtype.add("Cleric");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If a source would deal damage to a player, you may prevent X of that damage, where X is the number of Clerics you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BattletideAlchemistEffect()));
    }

    public BattletideAlchemist(final BattletideAlchemist card) {
        super(card);
    }

    @Override
    public BattletideAlchemist copy() {
        return new BattletideAlchemist(this);
    }
}

class BattletideAlchemistEffect extends PreventionEffectImpl {

    BattletideAlchemistEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "If a source would deal damage to a player, you may prevent X of that damage, where X is the number of Clerics you control";
    }

    BattletideAlchemistEffect(final BattletideAlchemistEffect effect) {
        super(effect);
    }

    @Override
    public BattletideAlchemistEffect copy() {
        return new BattletideAlchemistEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        boolean result = false;
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(event.getTargetId());
        if (controller != null) {
            int numberOfClericsControlled = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent("Cleric", "Clerics")).calculate(game, source, this);
            int toPrevent = Math.min(numberOfClericsControlled, event.getAmount());
            if (toPrevent > 0 && controller.chooseUse(Outcome.PreventDamage, "Prevent " + toPrevent + " damage to " + targetPlayer.getName() + '?', source, game)) {
                GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, targetPlayer.getId(), source.getSourceId(), source.getControllerId(), toPrevent, false);
                if (!game.replaceEvent(preventEvent)) {
                    if (event.getAmount() >= toPrevent) {
                        event.setAmount(event.getAmount() - toPrevent);
                    }
                    else {
                        event.setAmount(0);
                        result = true;
                    }
                    if (toPrevent > 0) {
                        game.informPlayers("Battletide Alchemist prevented " + toPrevent + " damage to " + targetPlayer.getName());
                        game.fireEvent(GameEvent.getEvent(
                                GameEvent.EventType.PREVENTED_DAMAGE,
                                targetPlayer.getId(),
                                source.getSourceId(),
                                source.getControllerId(),
                                toPrevent));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game);
    }
}
