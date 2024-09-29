package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FloweringOfTheWhiteTree extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creatures");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public FloweringOfTheWhiteTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // Legendary creatures you control get +2/+1 and have ward {1}.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                2, 1, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_LEGENDARY
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1)), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_LEGENDARY
        ).setText("and have ward {1}"));
        this.addAbility(ability);

        // Nonlegendary creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        )));
    }

    private FloweringOfTheWhiteTree(final FloweringOfTheWhiteTree card) {
        super(card);
    }

    @Override
    public FloweringOfTheWhiteTree copy() {
        return new FloweringOfTheWhiteTree(this);
    }
}
