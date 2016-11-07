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
package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SnakeToken;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

/**
 *
 * @author Rafbill
 */
public class CobraTrap extends CardImpl {

    public CobraTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{G}{G}");
        this.subtype.add("Trap");

        // If a noncreature permanent under your control was destroyed this turn by a spell or ability an opponent controlled, you may pay {G} rather than pay Cobra Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{G}"), CobraTrapCondition.getInstance()), new CobraTrapWatcher());

        // Create four 1/1 green Snake creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SnakeToken(), 4));
    }

    public CobraTrap(final CobraTrap card) {
        super(card);
    }

    @Override
    public CobraTrap copy() {
        return new CobraTrap(this);
    }
}

class CobraTrapCondition implements Condition {

    private static final CobraTrapCondition fInstance = new CobraTrapCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CobraTrapWatcher watcher = (CobraTrapWatcher) game.getState().getWatchers().get(CobraTrapWatcher.class.getName());
        return watcher != null && watcher.conditionMet(source.getControllerId());
    }

    @Override
    public String toString() {
        return "If a noncreature permanent under your control was destroyed this turn by a spell or ability an opponent controlled";
    }

}

class CobraTrapWatcher extends Watcher {

    Set<UUID> players = new HashSet<>();

    public CobraTrapWatcher() {
        super(CobraTrapWatcher.class.getName(), WatcherScope.GAME);
    }

    public CobraTrapWatcher(final CobraTrapWatcher watcher) {
        super(watcher);
    }

    @Override
    public CobraTrapWatcher copy() {
        return new CobraTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DESTROYED_PERMANENT) {
            Permanent perm = (Permanent) game.getPermanentOrLKIBattlefield(event.getTargetId()); // can regenerate or be indestructible
            if (perm != null && !perm.getCardType().contains(CardType.CREATURE)) {
                if (game.getStack().size() > 0) {
                    StackObject spell = game.getStack().getStackObject(event.getSourceId());
                    if (spell != null && game.getOpponents(perm.getControllerId()).contains(spell.getControllerId())) {
                        players.add(perm.getControllerId());
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    public boolean conditionMet(UUID playerId) {
        return players.contains(playerId);
    }
}
