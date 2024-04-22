package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgentOfTreachery extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public AgentOfTreachery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Agent of Treachery enters the battlefield, gain control of target permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.Custom));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // At the beginning of your end step, if you control three or more permanents you don't own, draw three cards.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new DrawCardSourceControllerEffect(3),
                        TargetController.YOU, false
                ), new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2), "At the beginning of your end step, " +
                "if you control three or more permanents you don't own, draw three cards."
        ).addHint(new ValueHint("Permanents you control but don't own", new PermanentsOnBattlefieldCount(filter))));
    }

    private AgentOfTreachery(final AgentOfTreachery card) {
        super(card);
    }

    @Override
    public AgentOfTreachery copy() {
        return new AgentOfTreachery(this);
    }
}
