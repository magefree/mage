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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DiscardControllerEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class SiftThroughSands extends CardImpl<SiftThroughSands> {

    private static final String rule = "If you've cast a spell named Peer Through Depths and a spell named Reach Through Mists this turn, you may search your library for a card named The Unspeakable, put it onto the battlefield, then shuffle your library";
    private static final FilterCreatureCard filter = new FilterCreatureCard("a card named The Unspeakable");
    static {
        filter.add(new NamePredicate("The Unspeakable"));
    }

    public SiftThroughSands(UUID ownerId) {
        super(ownerId, 84, "Sift Through Sands", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Arcane");

        this.color.setBlue(true);

        // Draw two cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        Effect effect = new DiscardControllerEffect(1);
        effect.setText(", then discard a card");
        this.getSpellAbility().addEffect(effect);
        // If you've cast a spell named Peer Through Depths and a spell named Reach Through Mists this turn, you may search your library for a card named The Unspeakable, put it onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false, true), new SiftThroughSandsCondition(), rule));
        this.addWatcher(new SiftThroughSandsWatcher());
    }

    public SiftThroughSands(final SiftThroughSands card) {
        super(card);
    }

    @Override
    public SiftThroughSands copy() {
        return new SiftThroughSands(this);
    }
}

class SiftThroughSandsCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        SiftThroughSandsWatcher watcher = (SiftThroughSandsWatcher) game.getState().getWatchers().get("SiftThroughSandsWatcher", source.getControllerId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }
}

class SiftThroughSandsWatcher extends WatcherImpl<SiftThroughSandsWatcher> {

    boolean castPeerThroughDepths = false;
    boolean castReachThroughMists = false;

    public SiftThroughSandsWatcher() {
        super("SiftThroughSandsWatcher", WatcherScope.PLAYER);
    }

    public SiftThroughSandsWatcher(final SiftThroughSandsWatcher watcher) {
        super(watcher);
        this.castPeerThroughDepths = watcher.castPeerThroughDepths;
        this.castReachThroughMists = watcher.castReachThroughMists;
    }

    @Override
    public SiftThroughSandsWatcher copy() {
        return new SiftThroughSandsWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { //no need to check - condition has already occured
            return;
        }
        if (event.getType() == EventType.SPELL_CAST
                && controllerId == event.getPlayerId()) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell.getCard().getName().equals("Peer Through Depths")) {
                castPeerThroughDepths = true;
            } else if (spell.getCard().getName().equals("Reach Through Mists")) {
                castReachThroughMists = true;
            }
            condition = castPeerThroughDepths && castReachThroughMists;
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.castPeerThroughDepths = false;
        this.castReachThroughMists = false;
    }
}
