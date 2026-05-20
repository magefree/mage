package mage.cards.r;

import java.util.UUID;

import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class ResonatingLute extends CardImpl {

    public ResonatingLute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}{R}");

        // Lands you control have "{T}: Add two mana of any one color. Spend this mana only to cast instant and sorcery spells."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            new ConditionalAnyColorManaAbility(
                    new TapSourceCost(), 2,
                    new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELLS_INSTANT_OR_SORCERY), true
            ),
            Duration.WhileOnBattlefield,
            StaticFilters.FILTER_LANDS
        )));

        // {T}: Draw a card. Activate only if you have seven or more cards in your hand.
        this.addAbility(new ActivateIfConditionActivatedAbility(
            new DrawCardSourceControllerEffect(1),
            new TapSourceCost(),
            new CardsInHandCondition(ComparisonType.OR_GREATER, 7)
        ));
    }

    private ResonatingLute(final ResonatingLute card) {
        super(card);
    }

    @Override
    public ResonatingLute copy() {
        return new ResonatingLute(this);
    }
}
