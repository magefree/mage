package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */


public final class SmeltWardGatekeepers extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    private static final FilterCreaturePermanent targetFilter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new SubtypePredicate(SubType.GATE));
        targetFilter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    private static final Condition gatesCondition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public SmeltWardGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Smelt-Ward Gatekeepers enters the battlefield, if you control two or more Gates, gain control of target creature an opponent controls until end of turn. Untap that creature. That creature gains haste until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn)),
                gatesCondition,
                "When {this} enters the battlefield, if you control two or more Gates, gain control of target creature an opponent controls until end of turn. Untap that creature. That creature gains haste until end of turn.");
        ability.addEffect(new UntapTargetEffect());
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        Target target = new TargetCreaturePermanent(targetFilter);
        ability.addTarget(target);
        ability.addHint(new ConditionHint(gatesCondition, "You control two or more Gates"));
        this.addAbility(ability);
    }

    public SmeltWardGatekeepers(final SmeltWardGatekeepers card) {
        super(card);
    }

    @Override
    public SmeltWardGatekeepers copy() {
        return new SmeltWardGatekeepers(this);
    }

}
