package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SkeletonToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathPriestOfMyrkul extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Skeletons, Vampires, and Zombies");

    static {
        filter.add(Predicates.or(
                SubType.SKELETON.getPredicate(),
                SubType.VAMPIRE.getPredicate(),
                SubType.ZOMBIE.getPredicate()
        ));
    }

    public DeathPriestOfMyrkul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Skeletons, Vampires, and Zombies you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));

        // At the beginning of your end step, if a creature died this turn, you may pay {1}. If you do, create a 1/1 black Skeleton creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new DoIfCostPaid(new CreateTokenEffect(new SkeletonToken()), new GenericManaCost(1)),
                TargetController.YOU, MorbidCondition.instance, false
        ).addHint(MorbidHint.instance));
    }

    private DeathPriestOfMyrkul(final DeathPriestOfMyrkul card) {
        super(card);
    }

    @Override
    public DeathPriestOfMyrkul copy() {
        return new DeathPriestOfMyrkul(this);
    }
}
