
package mage.cards.p;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PatriciansScorn extends CardImpl { 

    public PatriciansScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");


        // If you've cast another white spell this turn, you may cast this spell without paying its mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new CastWhiteSpellThisTurnCondition()), new PatriciansScornWatcher());
        // Destroy all enchantments.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENT));
    }

    private PatriciansScorn(final PatriciansScorn card) {
        super(card);
    }

    @Override
    public PatriciansScorn copy() {
        return new PatriciansScorn(this);
    }
}


class CastWhiteSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        PatriciansScornWatcher watcher = game.getState().getWatcher(PatriciansScornWatcher.class, source.getSourceId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }

    @Override
    public String toString() {
        return "If you've cast another white spell this turn";
    }
}

class PatriciansScornWatcher extends Watcher {

    private static final FilterSpell filter = new FilterSpell();
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public PatriciansScornWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition) { //no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST && controllerId.equals(event.getPlayerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, game)) {
                condition = true;
            }
        }
    }

}
