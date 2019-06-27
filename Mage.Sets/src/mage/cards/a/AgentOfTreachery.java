package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgentOfTreachery extends CardImpl {

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
                ), AgentOfTreacheryCondition.instance, "At the beginning of your end step, " +
                "if you control three or more permanents you don't own, draw three cards."
        ));
    }

    private AgentOfTreachery(final AgentOfTreachery card) {
        super(card);
    }

    @Override
    public AgentOfTreachery copy() {
        return new AgentOfTreachery(this);
    }
}

enum AgentOfTreacheryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield()
                .getAllActivePermanents(source.getControllerId())
                .stream()
                .filter(permanent -> !permanent.getOwnerId().equals(source.getControllerId()))
                .count() > 2;
    }
}
