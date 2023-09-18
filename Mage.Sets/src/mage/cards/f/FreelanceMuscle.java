package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FreelanceMuscle extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Greatest power and/or toughness among other creatures you control", FreelanceMuscleValue.instance
    );

    public FreelanceMuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Freelance Muscle attacks or blocks, it gets +X/+X until end of turn, where X is the greatest power and/or toughness among other creatures you control.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new BoostSourceEffect(
                FreelanceMuscleValue.instance, FreelanceMuscleValue.instance,
                Duration.EndOfTurn, true, "it"
        ), false).addHint(hint));
    }

    private FreelanceMuscle(final FreelanceMuscle card) {
        super(card);
    }

    @Override
    public FreelanceMuscle copy() {
        return new FreelanceMuscle(this);
    }
}

enum FreelanceMuscleValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .mapToInt(permanent -> Math.max(
                        permanent.getPower().getValue(),
                        permanent.getToughness().getValue()
                ))
                .max()
                .orElse(0);
    }

    @Override
    public FreelanceMuscleValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the greatest power and/or toughness among other creatures you control";
    }
}
