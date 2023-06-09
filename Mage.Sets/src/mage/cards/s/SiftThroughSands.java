
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
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

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SiftThroughSands extends CardImpl {

    private static final String rule = "<br>If you've cast a spell named Peer Through Depths and a spell named Reach Through Mists this turn, you may search your library for a card named The Unspeakable, put it onto the battlefield, then shuffle";
    private static final FilterCreatureCard filter = new FilterCreatureCard("a card named The Unspeakable");

    static {
        filter.add(new NamePredicate("The Unspeakable"));
    }

    public SiftThroughSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");
        this.subtype.add(SubType.ARCANE);

        // Draw two cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        Effect effect = new DiscardControllerEffect(1);
        effect.setText(", then discard a card");
        this.getSpellAbility().addEffect(effect);

        // If you've cast a spell named Peer Through Depths and a spell named Reach Through Mists this turn, you may search your library for a card named The Unspeakable, put it onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false), new SiftThroughSandsCondition(), rule));
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

class SiftThroughSandsCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        SiftThroughSandsWatcher watcher = game.getState().getWatcher(SiftThroughSandsWatcher.class, source.getControllerId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }
}

class SiftThroughSandsWatcher extends Watcher {

    boolean castPeerThroughDepths = false;
    boolean castReachThroughMists = false;

    public SiftThroughSandsWatcher() {
        super(WatcherScope.PLAYER);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition) { //no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && controllerId.equals(event.getPlayerId())) {
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
