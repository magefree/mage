package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NastyEnd extends CardImpl {

    public NastyEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // Draw two cards. If the sacrificed creature was legendary, draw three cards instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(3),
                new DrawCardSourceControllerEffect(2),
                NastyEndCondition.instance, "draw two cards. If the " +
                "sacrificed creature was legendary, draw three cards instead"
        ));
    }

    private NastyEnd(final NastyEnd card) {
        super(card);
    }

    @Override
    public NastyEnd copy() {
        return new NastyEnd(this);
    }
}

enum NastyEndCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .castStream(source.getCosts().stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .anyMatch(permanent -> permanent.isLegendary(game));
    }
}
