package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public class LegendarySpellAbility extends SimpleStaticAbility {

    public LegendarySpellAbility() {
        super(Zone.ALL, new LegendarySpellAbilityCheckEffect());
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
                        new SupertypePredicate(SuperType.LEGENDARY),
                        Predicates.or(
                                new CardTypePredicate(CardType.CREATURE),
                                new CardTypePredicate(CardType.PLANESWALKER)
                        )
                )
        );
    }

    public LegendarySpellAbilityCheckEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "<i>(You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)</i>";
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
                && !game.getBattlefield().contains(filter, event.getPlayerId(), 1, game);
    }

    @Override
    public LegendarySpellAbilityCheckEffect copy() {
        return new LegendarySpellAbilityCheckEffect(this);
    }

}
