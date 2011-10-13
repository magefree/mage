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

package mage.sets.zendikar;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetOpponent;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

import java.util.UUID;
import mage.Constants.WatcherScope;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ArchiveTrap extends CardImpl<ArchiveTrap> {

    public ArchiveTrap(UUID ownerId) {
        super(ownerId, 41, "Archive Trap", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");
        this.expansionSetCode = "ZEN";
        this.color.setBlue(true);
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new PutLibraryIntoGraveTargetEffect(13));
        this.getSpellAbility().addAlternativeCost(new ArchiveTrapAlternativeCost());
        this.addWatcher(new ArchiveTrapWatcher());
    }

    public ArchiveTrap(final ArchiveTrap card) {
        super(card);
    }

    @Override
    public ArchiveTrap copy() {
        return new ArchiveTrap(this);
    }

}

class ArchiveTrapWatcher extends WatcherImpl<ArchiveTrapWatcher> {

    public ArchiveTrapWatcher() {
        super("LibrarySearched", WatcherScope.PLAYER);
    }

    public ArchiveTrapWatcher(final ArchiveTrapWatcher watcher) {
        super(watcher);
    }

    @Override
    public ArchiveTrapWatcher copy() {
        return new ArchiveTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) //no need to check - condition has already occured
            return;
        if (event.getType() == EventType.LIBRARY_SEARCHED && game.getOpponents(controllerId).contains(event.getPlayerId()))
            condition = true;
    }

}

class ArchiveTrapAlternativeCost extends AlternativeCostImpl<ArchiveTrapAlternativeCost> {

    public ArchiveTrapAlternativeCost() {
        super("you may pay {0} rather than pay Archive Trap's mana cost");
        this.add(new GenericManaCost(0));
    }

    public ArchiveTrapAlternativeCost(final ArchiveTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public ArchiveTrapAlternativeCost copy() {
        return new ArchiveTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get("LibrarySearched", source.getControllerId());
        if (watcher != null && watcher.conditionMet())
            return true;
        return false;
    }

    @Override
    public String getText() {
        return "If an opponent searched his or her library this turn, you may pay {0} rather than pay Archive Trap's mana cost";
    }

}