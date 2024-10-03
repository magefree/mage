package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SiftThroughSands extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a card named The Unspeakable");

    static {
        filter.add(new NamePredicate("The Unspeakable"));
    }

    public SiftThroughSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.subtype.add(SubType.ARCANE);

        // Draw two cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));

        // If you've cast a spell named Peer Through Depths and a spell named Reach Through Mists this turn, you may search your library for a card named The Unspeakable, put it onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false),
                SiftThroughSandsCondition.instance, "<br>If you've cast a spell named " +
                "Peer Through Depths and a spell named Reach Through Mists this turn, you may search your library " +
                "for a card named The Unspeakable, put it onto the battlefield, then shuffle"
        ));
        this.getSpellAbility().addWatcher(new SiftThroughSandsWatcher());
    }

    private SiftThroughSands(final SiftThroughSands card) {
        super(card);
    }

    @Override
    public SiftThroughSands copy() {
        return new SiftThroughSands(this);
    }
}

enum SiftThroughSandsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return SiftThroughSandsWatcher.checkPlayer(source.getControllerId(), game);
    }
}

class SiftThroughSandsWatcher extends Watcher {

    Set<UUID> castPeerThroughDepths = new HashSet<>();
    Set<UUID> castReachThroughMists = new HashSet<>();

    public SiftThroughSandsWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell.hasName("Peer Through Depths", game)) {
            castPeerThroughDepths.add(spell.getControllerId());
        }
        if (spell.hasName("Reach Through Mists", game)) {
            castReachThroughMists.add(spell.getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.castPeerThroughDepths.clear();
        this.castReachThroughMists.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game.getState().getWatcher(SiftThroughSandsWatcher.class).check(playerId);
    }

    private boolean check(UUID playerId) {
        return castPeerThroughDepths.contains(playerId)
                && castReachThroughMists.contains(playerId);
    }
}
