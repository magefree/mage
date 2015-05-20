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
package mage.sets.bornofthegods;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class SpiritOfTheLabyrinth extends CardImpl {

    public SpiritOfTheLabyrinth(UUID ownerId) {
        super(ownerId, 27, "Spirit of the Labyrinth", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Spirit");

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Each player can't draw more than one card each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpiritOfTheLabyrinthEffect()), new SpiritOfTheLabyrinthWatcher());        
        
    }

    public SpiritOfTheLabyrinth(final SpiritOfTheLabyrinth card) {
        super(card);
    }

    @Override
    public SpiritOfTheLabyrinth copy() {
        return new SpiritOfTheLabyrinth(this);
    }
}

class SpiritOfTheLabyrinthWatcher extends Watcher {

    private final HashSet<UUID> playersThatDrewCard;
    
    public SpiritOfTheLabyrinthWatcher() {
        super("DrewCard", WatcherScope.GAME);
        this.playersThatDrewCard = new HashSet<>();
    }

    public SpiritOfTheLabyrinthWatcher(final SpiritOfTheLabyrinthWatcher watcher) {
        super(watcher);
        this.playersThatDrewCard = new HashSet<>();
        playersThatDrewCard.addAll(watcher.playersThatDrewCard);
    }

    @Override
    public SpiritOfTheLabyrinthWatcher copy() {
        return new SpiritOfTheLabyrinthWatcher(this);
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

class SpiritOfTheLabyrinthEffect extends ContinuousRuleModifyingEffectImpl {

    public SpiritOfTheLabyrinthEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "Each player can't draw more than one card each turn";
    }

    public SpiritOfTheLabyrinthEffect(final SpiritOfTheLabyrinthEffect effect) {
        super(effect);
    }

    @Override
    public SpiritOfTheLabyrinthEffect copy() {
        return new SpiritOfTheLabyrinthEffect(this);
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
        SpiritOfTheLabyrinthWatcher watcher = (SpiritOfTheLabyrinthWatcher) game.getState().getWatchers().get("DrewCard");
        if (watcher != null && watcher.hasPlayerDrewCardThisTurn(event.getPlayerId())) {
            return true;
        }
        return false;
    }

}
