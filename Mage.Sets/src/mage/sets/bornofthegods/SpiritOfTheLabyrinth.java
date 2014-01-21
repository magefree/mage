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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
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
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class SpiritOfTheLabyrinth extends CardImpl<SpiritOfTheLabyrinth> {

    public SpiritOfTheLabyrinth(UUID ownerId) {
        super(ownerId, 27, "Spirit of the Labyrinth", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Spirit");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Each player can't draw more than one card each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpiritOfTheLabyrinthEffect()));
        this.addWatcher(new SpiritOfTheLabyrinthWatcher());        
        
    }

    public SpiritOfTheLabyrinth(final SpiritOfTheLabyrinth card) {
        super(card);
    }

    @Override
    public SpiritOfTheLabyrinth copy() {
        return new SpiritOfTheLabyrinth(this);
    }
}

class SpiritOfTheLabyrinthWatcher extends WatcherImpl<SpiritOfTheLabyrinthWatcher> {

    public SpiritOfTheLabyrinthWatcher() {
        super("DrewCard", WatcherScope.PLAYER);
    }

    public SpiritOfTheLabyrinthWatcher(final SpiritOfTheLabyrinthWatcher watcher) {
        super(watcher);
    }

    @Override
    public SpiritOfTheLabyrinthWatcher copy() {
        return new SpiritOfTheLabyrinthWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) {//no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.DREW_CARD ) {
            if (event.getPlayerId().equals(this.getControllerId())) {
                condition = true;
            }
        }
    }

}

class SpiritOfTheLabyrinthEffect extends ReplacementEffectImpl<SpiritOfTheLabyrinthEffect> {

    public SpiritOfTheLabyrinthEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
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
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DRAW_CARD) {
            Watcher watcher = game.getState().getWatchers().get("DrewCard", event.getPlayerId());
            if (watcher != null && watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

}
