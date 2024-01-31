package mage.cards.c;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author DominionSpy
 */
public final class CaseOfTheRansackedLab extends CardImpl {

    private static final FilterCard filter = new FilterCard("Instant and sorcery spells");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public CaseOfTheRansackedLab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.CASE);

        // Instant and sorcery spells you cast cost {1} less to cast.
        Ability initialAbility = new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1));
        // To solve -- You've cast four or more instant and sorcery spells this turn.
        // Solved -- Whenever you cast an instant or sorcery spell, draw a card.
        Ability solvedAbility = new ConditionalTriggeredAbility(
                new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1),
                        StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false),
                SolvedSourceCondition.SOLVED, "");

        this.addAbility(new CaseAbility(initialAbility, CaseOfTheRansackedLabCondition.instance, solvedAbility)
                        .addHint(new CaseOfTheRansackedLabHint(CaseOfTheRansackedLabCondition.instance)),
                new CaseOfTheRansackedLabWatcher());
    }

    private CaseOfTheRansackedLab(final CaseOfTheRansackedLab card) {
        super(card);
    }

    @Override
    public CaseOfTheRansackedLab copy() {
        return new CaseOfTheRansackedLab(this);
    }
}

class CaseOfTheRansackedLabWatcher extends Watcher {

    private final Map<UUID, Integer> instantSorceryCount = new HashMap<>();

    CaseOfTheRansackedLabWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell == null || !spell.isInstantOrSorcery(game)) {
                return;
            }
            this.instantSorceryCount.putIfAbsent(spell.getControllerId(), 0);
            this.instantSorceryCount.compute(
                    spell.getControllerId(), (k, a) -> a + 1
            );
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.instantSorceryCount.clear();
    }

    int getInstantSorceryCount(UUID playerId) {
        return this.instantSorceryCount.getOrDefault(playerId, 0);
    }
}

enum CaseOfTheRansackedLabCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CaseOfTheRansackedLabWatcher watcher = game.getState().getWatcher(CaseOfTheRansackedLabWatcher.class);
        return watcher != null && watcher.getInstantSorceryCount(source.getControllerId()) > 3;
    }

    @Override
    public String toString() {
        return "You've cast four or more instant and sorcery spells this turn";
    }
}

class CaseOfTheRansackedLabHint extends CaseSolvedHint {

    CaseOfTheRansackedLabHint(Condition condition) {
        super(condition);
    }

    private CaseOfTheRansackedLabHint(final CaseOfTheRansackedLabHint hint) {
        super(hint);
    }

    @Override
    public CaseOfTheRansackedLabHint copy() {
        return new CaseOfTheRansackedLabHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability ability) {
        int spells = game.getState().getWatcher(CaseOfTheRansackedLabWatcher.class)
                .getInstantSorceryCount(ability.getControllerId());
        return "Instant and sorcery spells cast: " + spells + " (need 4).";
    }
}
