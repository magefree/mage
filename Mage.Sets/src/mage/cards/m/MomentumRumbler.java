package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MomentumRumbler extends CardImpl {

    public MomentumRumbler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Momentum Rumbler attacks, if it doesn't have first strike, put a first strike counter on it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new AddCountersSourceEffect(
                        CounterType.FIRST_STRIKE.createInstance()
                ), false), MomentumRumblerCondition.FALSE, "Whenever {this} attacks, " +
                "if it doesn't have first strike, put a first strike counter on it."
        ));

        // Whenever Momentum Rumbler attacks, if it has first strike, it gains double strike until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new GainAbilitySourceEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
                ), false), MomentumRumblerCondition.TRUE, "Whenever {this} attacks, " +
                "if it has first strike, it gains double strike until end of turn."
        ));
    }

    private MomentumRumbler(final MomentumRumbler card) {
        super(card);
    }

    @Override
    public MomentumRumbler copy() {
        return new MomentumRumbler(this);
    }
}

enum MomentumRumblerCondition implements Condition {
    TRUE(true),
    FALSE(false);

    private final boolean hasAbility;

    MomentumRumblerCondition(boolean hasAbility) {
        this.hasAbility = hasAbility;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        return hasAbility == permanent.hasAbility(FirstStrikeAbility.getInstance(), game);
    }
}