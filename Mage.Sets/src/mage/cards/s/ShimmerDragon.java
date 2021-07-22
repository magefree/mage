package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShimmerDragon extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, ComparisonType.MORE_THAN, 3
    );
    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("untapped artifacts you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ShimmerDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as you control four or more artifacts, Shimmer Dragon has hexproof.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        HexproofAbility.getInstance(), Duration.WhileOnBattlefield
                ), condition, "as long as you control four or more artifacts, {this} has hexproof"
        )));

        // Tap two untapped artifacts you control: Draw a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new TapTargetCost(new TargetControlledPermanent(2, filter))
        ));
    }

    private ShimmerDragon(final ShimmerDragon card) {
        super(card);
    }

    @Override
    public ShimmerDragon copy() {
        return new ShimmerDragon(this);
    }
}
