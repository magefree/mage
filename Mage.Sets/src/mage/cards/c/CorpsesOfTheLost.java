package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SkeletonPirateToken;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CorpsesOfTheLost extends CardImpl {

    private static final FilterCreaturePermanent filter =
            new FilterCreaturePermanent(SubType.SKELETON, "Skeletons you control");
    private static final FilterPermanent filter2 =
            new FilterPermanent(SubType.SKELETON, "Skeletons you control");

    public CorpsesOfTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Skeletons you control get +1/+0 and have haste.
        Ability ability = new SimpleStaticAbility(
                new BoostControlledEffect(
                        1, 0, Duration.WhileOnBattlefield,
                        filter, false
                )
        );
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(),
                Duration.WhileOnBattlefield,
                filter2
        ).setText("and have haste"));
        this.addAbility(ability);

        // When Corpses of the Lost enters the battlefield, create a 2/2 black Skeleton Pirate creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SkeletonPirateToken())));

        // At the beginning of your end step, if you descended this turn, you may pay 1 life. If you do, return Corpses of the Lost to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DoIfCostPaid(
                        new ReturnToHandSourceEffect(),
                        new PayLifeCost(1)
                ), TargetController.YOU, DescendedThisTurnCondition.instance, false
        ).addHint(DescendedThisTurnCount.getHint()), new DescendedWatcher());
    }

    private CorpsesOfTheLost(final CorpsesOfTheLost card) {
        super(card);
    }

    @Override
    public CorpsesOfTheLost copy() {
        return new CorpsesOfTheLost(this);
    }
}
