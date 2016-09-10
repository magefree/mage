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
package mage.sets.conspiracytakethecrown;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author maxlebedev
 */
public class LeovoldEmissaryOfTrest extends CardImpl {

    public LeovoldEmissaryOfTrest(UUID ownerId) {
        super(ownerId, 77, "Leovold, Emissary of Trest", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");
        this.expansionSetCode = "CN2";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Advisor");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each opponent can't draw more than one card each turn.  (Based on SpiritOfTheLabyrinth)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeovoldEmissaryOfTrestEffect()), new LeovoldEmissaryOfTrestWatcher());

        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new LeovoldEmissaryOfTrestTriggeredAbility());
    }

    public LeovoldEmissaryOfTrest(final LeovoldEmissaryOfTrest card) {
        super(card);
    }

    @Override
    public LeovoldEmissaryOfTrest copy() {
        return new LeovoldEmissaryOfTrest(this);
    }
}


class LeovoldEmissaryOfTrestWatcher extends Watcher {

    private final HashSet<UUID> playersThatDrewCard;

    public LeovoldEmissaryOfTrestWatcher() {
        super("DrewCard", WatcherScope.GAME);
        this.playersThatDrewCard = new HashSet<>();
    }

    public LeovoldEmissaryOfTrestWatcher(final LeovoldEmissaryOfTrestWatcher watcher) {
        super(watcher);
        this.playersThatDrewCard = new HashSet<>();
        playersThatDrewCard.addAll(watcher.playersThatDrewCard);
    }

    @Override
    public LeovoldEmissaryOfTrestWatcher copy() {
        return new LeovoldEmissaryOfTrestWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD ) {
            if (!playersThatDrewCard.contains(event.getPlayerId())) {
                playersThatDrewCard.add(event.getPlayerId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playersThatDrewCard.clear();
    }

    public boolean hasPlayerDrewCardThisTurn(UUID playerId) {
        return playersThatDrewCard.contains(playerId);
    }

}

class LeovoldEmissaryOfTrestEffect extends ContinuousRuleModifyingEffectImpl {

    public LeovoldEmissaryOfTrestEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "Each opponent can't draw more than one card each turn";
    }

    public LeovoldEmissaryOfTrestEffect(final LeovoldEmissaryOfTrestEffect effect) {
        super(effect);
    }

    @Override
    public LeovoldEmissaryOfTrestEffect copy() {
        return new LeovoldEmissaryOfTrestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        LeovoldEmissaryOfTrestWatcher watcher = (LeovoldEmissaryOfTrestWatcher) game.getState().getWatchers().get("DrewCard");

        Player controller = game.getPlayer(source.getControllerId());
        return watcher != null && controller != null && watcher.hasPlayerDrewCardThisTurn(event.getPlayerId())
                && game.isOpponent(controller, event.getPlayerId());
    }

}

class LeovoldEmissaryOfTrestTriggeredAbility extends TriggeredAbilityImpl {

    LeovoldEmissaryOfTrestTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    LeovoldEmissaryOfTrestTriggeredAbility(final LeovoldEmissaryOfTrestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LeovoldEmissaryOfTrestTriggeredAbility copy() {
        return new LeovoldEmissaryOfTrestTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        Player targetter = game.getPlayer(event.getPlayerId());
        if (controller != null && targetter != null && !controller.getId().equals(targetter.getId())) {
            if (event.getTargetId().equals(controller.getId())) {
                return true;
            }
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null && this.getControllerId().equals(permanent.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.";
    }
}
