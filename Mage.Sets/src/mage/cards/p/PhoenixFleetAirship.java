package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.PermanentsSacrificedThisTurnCount;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.hint.common.PermanentsSacrificedThisTurnHint;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhoenixFleetAirship extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new NamePredicate("Phoenix Fleet Airship"));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 7);
    private static final Hint hint = new ValueHint(
            "Permanents you control named Phoenix Fleet Airship", new PermanentsOnBattlefieldCount(filter)
    );

    public PhoenixFleetAirship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, if you sacrificed a permanent this turn, create a token that's a copy of this Vehicle.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenCopySourceEffect())
                .withInterveningIf(PhoenixFleetAirshipCondition.instance)
                .addHint(PermanentsSacrificedThisTurnHint.instance), new PermanentsSacrificedWatcher());

        // As long as you control eight or more permanents named Phoenix Fleet Airship, this Vehicle is an artifact creature.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new AddCardTypeSourceEffect(Duration.WhileOnBattlefield, CardType.ARTIFACT, CardType.CREATURE),
                condition, "as long as you control eight or more permanents " +
                "named Phoenix Fleet Airship, this Vehicle is an artifact creature"
        )).addHint(hint));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private PhoenixFleetAirship(final PhoenixFleetAirship card) {
        super(card);
    }

    @Override
    public PhoenixFleetAirship copy() {
        return new PhoenixFleetAirship(this);
    }
}

enum PhoenixFleetAirshipCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return PermanentsSacrificedThisTurnCount.instance.calculate(game, source, null) > 0;
    }

    @Override
    public String toString() {
        return "you sacrificed a permanent this turn";
    }
}
