package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class UbulSarGatekeepers extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    private static final Condition gatesCondition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public UbulSarGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Ubul Sar Gatekeepers enters the battlefield, if you control two or more Gates, target creature an opponent controls gets -2/-2 until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-2, -2, Duration.EndOfTurn)),
                gatesCondition,
                "Whenever {this} enters the battlefield, if you control two or more Gates, target creature an opponent controls gets -2/-2 until end of turn.");
        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        ability.addHint(new ConditionHint(gatesCondition, "You control two or more Gates"));
        this.addAbility(ability);
    }

    private UbulSarGatekeepers(final UbulSarGatekeepers card) {
        super(card);
    }

    @Override
    public UbulSarGatekeepers copy() {
        return new UbulSarGatekeepers(this);
    }

}
