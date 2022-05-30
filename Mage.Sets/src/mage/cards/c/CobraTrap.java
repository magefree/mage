
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SnakeToken;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Rafbill
 */
public final class CobraTrap extends CardImpl {

    public CobraTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}{G}");
        this.subtype.add(SubType.TRAP);

        // If a noncreature permanent under your control was destroyed this turn by a spell or ability an opponent controlled, you may pay {G} rather than pay Cobra Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{G}"), CobraTrapCondition.instance), new CobraTrapWatcher());

        // Create four 1/1 green Snake creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SnakeToken(), 4));
    }

    private CobraTrap(final CobraTrap card) {
        super(card);
    }

    @Override
    public CobraTrap copy() {
        return new CobraTrap(this);
    }
}

enum CobraTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CobraTrapWatcher watcher = game.getState().getWatcher(CobraTrapWatcher.class);
        return watcher != null && watcher.conditionMet(source.getControllerId());
    }

    @Override
    public String toString() {
        return "If a noncreature permanent under your control was destroyed this turn by a spell or ability an opponent controlled";
    }

}

class CobraTrapWatcher extends Watcher {

    private Set<UUID> players = new HashSet<>();

    public CobraTrapWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DESTROYED_PERMANENT) {
            Permanent perm = game.getPermanentOrLKIBattlefield(event.getTargetId()); // can regenerate or be indestructible
            if (perm != null && !perm.isCreature(game)) {
                if (!game.getStack().isEmpty()) {
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
