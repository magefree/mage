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
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
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
public class TalarasBattalion extends CardImpl<TalarasBattalion> {

    public TalarasBattalion(UUID ownerId) {
        super(ownerId, 77, "Talara's Battalion", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elf");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cast Talara's Battalion only if you've cast another green spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TalarasBattalionEffect()));
        this.addWatcher(new TalarasBattalionWatcher(this.getId()));

    }

    public TalarasBattalion(final TalarasBattalion card) {
        super(card);
    }

    @Override
    public TalarasBattalion copy() {
        return new TalarasBattalion(this);
    }
}

class TalarasBattalionEffect extends ReplacementEffectImpl<TalarasBattalionEffect> {

    TalarasBattalionEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast {this} only if you've cast another green spell this turn";
    }

    TalarasBattalionEffect(final TalarasBattalionEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL
                && event.getSourceId().equals(source.getSourceId())) {
            CastGreenSpellThisTurnCondition condition = new CastGreenSpellThisTurnCondition();
            return (!condition.apply(game, source));
        }
        return false;

    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TalarasBattalionEffect copy() {
        return new TalarasBattalionEffect(this);
    }
}

class CastGreenSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        TalarasBattalionWatcher watcher = (TalarasBattalionWatcher) game.getState().getWatchers().get("TalarasBattalionWatcher", source.getControllerId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }
}

class TalarasBattalionWatcher extends WatcherImpl<TalarasBattalionWatcher> {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }
    private UUID cardId;

    public TalarasBattalionWatcher(UUID cardId) {
        super("TalarasBattalionWatcher", WatcherScope.PLAYER);
        this.cardId = cardId;
    }

    public TalarasBattalionWatcher(final TalarasBattalionWatcher watcher) {
        super(watcher);
        this.cardId = watcher.cardId;
    }

    @Override
    public TalarasBattalionWatcher copy() {
        return new TalarasBattalionWatcher(this);
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
