package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladebackSliver extends CardImpl {

    public BladebackSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Hellbent — As long as you have no cards in hand, Sliver creatures you control have "{T}: This creature deals 1 damage to target player or planeswalker."
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1, "this creature"), new TapSourceCost()
        );
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        ability, Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_ALL_SLIVERS
                ), HellbentCondition.instance, "<i>Hellbent</i> &mdash; " +
                "As long as you have no cards in hand, Sliver creatures you control have " +
                "\"{T}: This creature deals 1 damage to target player or planeswalker.\""
        )));
    }

    private BladebackSliver(final BladebackSliver card) {
        super(card);
    }

    @Override
    public BladebackSliver copy() {
        return new BladebackSliver(this);
    }
}
