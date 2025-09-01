package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
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

    private static final FilterPermanent filter = new FilterControlledPermanent("you control three or more permanents you don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ValueHint("Permanents you control but don't own", new PermanentsOnBattlefieldCount(filter));

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
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(3)).withInterveningIf(condition).addHint(hint));
    }

    private AgentOfTreachery(final AgentOfTreachery card) {
        super(card);
    }

    @Override
    public AgentOfTreachery copy() {
        return new AgentOfTreachery(this);
    }
}
