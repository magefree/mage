package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class RoadsideReliquary extends CardImpl {

    private static final Condition artifactCondition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_ARTIFACT);
    private static final Condition enchantmentCondition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT);
    private static final Hint artifactHint = new ConditionHint(artifactCondition, "You control an artifact");
    private static final Hint enchantmentHint = new ConditionHint(enchantmentCondition, "You control an enchantment");

    public RoadsideReliquary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}, Sacrifice Roadside Reliquary: Draw a card if you control an artifact. Draw a card if you control an enchantment.
        Ability ability = new SimpleActivatedAbility(
                new ConditionalOneShotEffect(
                        new DrawCardSourceControllerEffect(1),
                        artifactCondition,
                        "Draw a card if you control an artifact"
                ),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                enchantmentCondition,
                "Draw a card if you control an enchantment"
        ));
        ability.addHint(artifactHint);
        ability.addHint(enchantmentHint);
        this.addAbility(ability);
    }

    private RoadsideReliquary(final RoadsideReliquary card) {
        super(card);
    }

    @Override
    public RoadsideReliquary copy() {
        return new RoadsideReliquary(this);
    }
}
