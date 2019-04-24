
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetCard;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Rafbill
 */
public final class SummoningTrap extends CardImpl {

    public SummoningTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{G}{G}");
        this.subtype.add(SubType.TRAP);

        // If a creature spell you cast this turn was countered by a spell or ability an opponent controlled, you may pay {0} rather than pay Summoning Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{0}"), SummoningTrapCondition.instance), new SummoningTrapWatcher());

        // Look at the top seven cards of your library. You may put a creature card from among them onto the battlefield. Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new SummoningTrapEffect());
    }

    public SummoningTrap(final SummoningTrap card) {
        super(card);
    }

    @Override
    public SummoningTrap copy() {
        return new SummoningTrap(this);
    }
}

enum SummoningTrapCondition implements Condition {

   instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SummoningTrapWatcher watcher = (SummoningTrapWatcher) game.getState().getWatchers().get(SummoningTrapWatcher.class.getSimpleName());
        return watcher != null && watcher.creatureSpellOfPlayerWasCountered(source.getControllerId());
    }

    @Override
    public String toString() {
        return "If a creature spell you cast this turn was countered by a spell or ability an opponent controlled";
    }
}

class SummoningTrapWatcher extends Watcher {

    Set<UUID> players = new HashSet<>();

    public SummoningTrapWatcher() {
        super(SummoningTrapWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public SummoningTrapWatcher(final SummoningTrapWatcher watcher) {
        super(watcher);
        this.players.addAll(watcher.players);
    }

    @Override
    public SummoningTrapWatcher copy() {
        return new SummoningTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.COUNTERED) {
            StackObject counteredSpell = game.getStack().getStackObject(event.getTargetId());
            if (counteredSpell == null) {
                counteredSpell = (StackObject) game.getLastKnownInformation(event.getTargetId(), Zone.STACK);
            }
            if (counteredSpell instanceof Spell
                    && !players.contains(counteredSpell.getControllerId())
                    && counteredSpell.isCreature()) {
                StackObject counteringStackObject = game.getStack().getStackObject(event.getSourceId());
                if (counteringStackObject == null) {
                    counteringStackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
                }
                if (counteringStackObject != null && game.getOpponents(counteredSpell.getControllerId()).contains(counteringStackObject.getControllerId())) {
                    players.add(counteredSpell.getControllerId());
                }
            }

        }
    }

    public boolean creatureSpellOfPlayerWasCountered(UUID playerId) {
        return players.contains(playerId);
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }
}

class SummoningTrapEffect extends OneShotEffect {

    public SummoningTrapEffect(final SummoningTrapEffect effect) {
        super(effect);
    }

    public SummoningTrapEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Look at the top seven cards of your library. You may put a creature card from among them onto the battlefield. Put the rest on the bottom of your library in any order";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 7));
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(Zone.LIBRARY,
                    new FilterCreatureCard(
                            "creature card to put on the battlefield"));
            if (controller.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            if (!cards.isEmpty()) {
                controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            }
        }
        return true;
    }

    @Override
    public SummoningTrapEffect copy() {
        return new SummoningTrapEffect(this);
    }
}
