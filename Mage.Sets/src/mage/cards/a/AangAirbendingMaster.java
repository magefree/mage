package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OneOrMoreLeaveWithoutDyingTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AllyToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangAirbendingMaster extends CardImpl {

    private static final DynamicValue xValue = new CountersControllerCount(CounterType.EXPERIENCE);

    public AangAirbendingMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Aang enters, airbend another target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AirbendTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // Whenever one or more creatures you control leave the battlefield without dying, you get an experience counter.
        this.addAbility(new OneOrMoreLeaveWithoutDyingTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU),
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ));

        // At the beginning of your upkeep, create a 1/1 white Ally creature token for each experience counter you have.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new AllyToken(), xValue)
                .setText("create a 1/1 white Ally creature token for each experience counter you have")));
    }

    private AangAirbendingMaster(final AangAirbendingMaster card) {
        super(card);
    }

    @Override
    public AangAirbendingMaster copy() {
        return new AangAirbendingMaster(this);
    }
}
