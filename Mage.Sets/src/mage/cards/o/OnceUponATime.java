package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OnceUponATime extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature or land card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)
        ));
    }

    public OnceUponATime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // If this spell is the first spell you've cast this game, you may cast it without paying its mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                null, OnceUponATimeCondition.instance, "If this spell is the first spell " +
                "you've cast this game, you may cast it without paying its mana cost."
        ), new OnceUponATimeWatcher());

        // Look at the top five cards of your library. You may reveal a creature or land card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                new StaticValue(5), false, new StaticValue(1), filter,
                Zone.LIBRARY, false, true, false, Zone.HAND,
                true, false, false
        ).setBackInRandomOrder(true).setText("Look at the top five cards of your library. " +
                "You may reveal a creature or land card from among them and put it into your hand. " +
                "Put the rest on the bottom of your library in a random order."
        ));
    }

    private OnceUponATime(final OnceUponATime card) {
        super(card);
    }

    @Override
    public OnceUponATime copy() {
        return new OnceUponATime(this);
    }
}

enum OnceUponATimeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        OnceUponATimeWatcher watcher = game.getState().getWatcher(OnceUponATimeWatcher.class);
        return watcher != null && watcher.getSpellsCastThisTurn(source.getControllerId());
    }
}

class OnceUponATimeWatcher extends Watcher {

    private final Set<UUID> castSpells = new HashSet();

    OnceUponATimeWatcher() {
        super(WatcherScope.GAME);
    }

    private OnceUponATimeWatcher(final OnceUponATimeWatcher watcher) {
        super(watcher);
        this.castSpells.addAll(watcher.castSpells);
    }

    @Override
    public OnceUponATimeWatcher copy() {
        return new OnceUponATimeWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST == event.getType()) {
            castSpells.add(event.getPlayerId());
        }
    }

    public boolean getSpellsCastThisTurn(UUID playerId) {
        return !castSpells.contains(playerId);
    }
}
