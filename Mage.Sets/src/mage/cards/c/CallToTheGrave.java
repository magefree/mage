package mage.cards.c;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class CallToTheGrave extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zombie creature");

    static {
        filter.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterCreaturePermanent("no creatures are on the battlefield"), ComparisonType.EQUAL_TO, 0, false
    );

    public CallToTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        // At the beginning of each player's upkeep, that player sacrifices a non-Zombie creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.EACH_PLAYER, new SacrificeEffect(filter, 1, "that player"), false
        ));

        // At the beginning of the end step, if no creatures are on the battlefield, sacrifice Call to the Grave.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect(), false
        ).withInterveningIf(condition));
    }

    private CallToTheGrave(final CallToTheGrave card) {
        super(card);
    }

    @Override
    public CallToTheGrave copy() {
        return new CallToTheGrave(this);
    }
}
