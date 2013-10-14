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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class SoulReap extends CardImpl<SoulReap> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nongreen creature");
    
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.GREEN)));
    }
    
    private String rule = "Its controller loses 3 life if you've cast another black spell this turn";

    public SoulReap(UUID ownerId) {
        super(ownerId, 44, "Soul Reap", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}");
        this.expansionSetCode = "EVE";

        this.color.setBlack(true);

        // Destroy target nongreen creature. Its controller loses 3 life if you've cast another black spell this turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new SoulReapEffect(), new CastBlackSpellThisTurnCondition(), rule));
        this.addWatcher(new SoulReapWatcher(this.getId()));
        
    }

    public SoulReap(final SoulReap card) {
        super(card);
    }

    @Override
    public SoulReap copy() {
        return new SoulReap(this);
    }
}

class CastBlackSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        SoulReapWatcher watcher = (SoulReapWatcher) game.getState().getWatchers().get("SoulReapWatcher", source.getControllerId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }
}

class SoulReapWatcher extends WatcherImpl<SoulReapWatcher> {

    private static final FilterSpell filter = new FilterSpell();
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    private UUID cardId;

    public SoulReapWatcher(UUID cardId) {
        super("SoulReapWatcher", WatcherScope.PLAYER);
        this.cardId = cardId;
    }

    public SoulReapWatcher(final SoulReapWatcher watcher) {
        super(watcher);
        this.cardId = watcher.cardId;
    }

    @Override
    public SoulReapWatcher copy() {
        return new SoulReapWatcher(this);
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

class SoulReapEffect extends OneShotEffect<SoulReapEffect> {

    public SoulReapEffect() {
        super(Outcome.Detriment);
        this.staticText = "Its controller loses 3 life if you've cast another black spell this turn";
    }

    public SoulReapEffect(final SoulReapEffect effect) {
        super(effect);
    }

    @Override
    public SoulReapEffect copy() {
        return new SoulReapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                controller.loseLife(3, game);
                return true;
            }
        }
        return false;
    }
}