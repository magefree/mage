package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public class LegendarySpellAbility extends SimpleStaticAbility {

    public LegendarySpellAbility() {
        this(false);
    }

    public LegendarySpellAbility(boolean isInstant) {
        super(Zone.ALL, new LegendarySpellAbilityCheckEffect(isInstant));
        this.setRuleAtTheTop(true);
    }

    private LegendarySpellAbility(final LegendarySpellAbility ability) {
        super(ability);
    }

    @Override
    public LegendarySpellAbility copy() {
        return new LegendarySpellAbility(this);
    }
}

class LegendarySpellAbilityCheckEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent("legendary creature or planeswalker");

    static {
        filter.add(
                Predicates.and(
                        SuperType.LEGENDARY.getPredicate(),
                        Predicates.or(
                                CardType.CREATURE.getPredicate(),
                                CardType.PLANESWALKER.getPredicate()
                        )
                )
        );
    }

    public LegendarySpellAbilityCheckEffect(boolean isInstant) {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "<i>(You may cast a legendary " +
                (isInstant ? "instant" : "sorcery") +
                " only if you control a legendary creature or planeswalker.)</i>";
    }

    private LegendarySpellAbilityCheckEffect(final LegendarySpellAbilityCheckEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId())
                && !game.getBattlefield().containsControlled(filter, source, game, 1);
    }

    @Override
    public LegendarySpellAbilityCheckEffect copy() {
        return new LegendarySpellAbilityCheckEffect(this);
    }

}
