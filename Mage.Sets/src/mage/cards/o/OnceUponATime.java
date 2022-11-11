package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
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

    public OnceUponATime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // If this spell is the first spell you've cast this game, you may cast it without paying its mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                null, OnceUponATimeCondition.instance, "If this spell is the first spell " +
                "you've cast this game, you may cast it without paying its mana cost."
        ), new OnceUponATimeWatcher());

        // Look at the top five cards of your library. You may reveal a creature or land card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_CREATURE_OR_LAND, PutCards.HAND, PutCards.BOTTOM_RANDOM));
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
        return watcher != null && watcher.getSpellsCastThisGame(source.getControllerId());
    }
}

class OnceUponATimeWatcher extends Watcher {

    private final Set<UUID> castSpells = new HashSet();

    OnceUponATimeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
                castSpells.add(event.getPlayerId());
                return;
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    castSpells.clear();
                }
        }
    }

    boolean getSpellsCastThisGame(UUID playerId) {
        return !castSpells.contains(playerId);
    }
}
