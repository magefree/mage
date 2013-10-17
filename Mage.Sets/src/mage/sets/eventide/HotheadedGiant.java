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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class HotheadedGiant extends CardImpl<HotheadedGiant> {

    public HotheadedGiant(UUID ownerId) {
        super(ownerId, 57, "Hotheaded Giant", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Giant");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Hotheaded Giant enters the battlefield with two -1/-1 counters on it unless you've cast another red spell this turn.
        Condition condition = new CastRedSpellThisTurnCondition();
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)), new InvertCondition(condition), ""), "with two -1/-1 counters on it unless you've cast another red spell this turn"));
        this.addWatcher(new HotHeadedGiantWatcher(this.getId()));
        
    }

    public HotheadedGiant(final HotheadedGiant card) {
        super(card);
    }

    @Override
    public HotheadedGiant copy() {
        return new HotheadedGiant(this);
    }
}

class CastRedSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        HotHeadedGiantWatcher watcher = (HotHeadedGiantWatcher) game.getState().getWatchers().get("HotHeadedGiantWatcher", source.getControllerId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }
}

class HotHeadedGiantWatcher extends WatcherImpl<HotHeadedGiantWatcher> {

    private static final FilterSpell filter = new FilterSpell();
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    private UUID cardId;

    public HotHeadedGiantWatcher(UUID cardId) {
        super("HotHeadedGiantWatcher", WatcherScope.PLAYER);
        this.cardId = cardId;
    }

    public HotHeadedGiantWatcher(final HotHeadedGiantWatcher watcher) {
        super(watcher);
        this.cardId = watcher.cardId;
    }

    @Override
    public HotHeadedGiantWatcher copy() {
        return new HotHeadedGiantWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { //no need to check - condition has already occured
            return;
        }
        if (event.getType() == EventType.SPELL_CAST
                && controllerId == event.getPlayerId()) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (!spell.getSourceId().equals(cardId) && filter.match(spell, game)) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        condition = false;
    }
}
