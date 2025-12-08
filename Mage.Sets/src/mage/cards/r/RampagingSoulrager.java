package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
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
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampagingSoulrager extends CardImpl {

    public RampagingSoulrager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // This creature gets +3/+0 as long as there are two or more unlocked doors among Rooms you control.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield),
                RampagingSoulragerCondition.instance, "{this} gets +3/+0 as long as " +
                "there are two or more unlocked doors among Rooms you control"
        )).addHint(RampagingSoulragerValue.getHint()));
    }

    private RampagingSoulrager(final RampagingSoulrager card) {
        super(card);
    }

    @Override
    public RampagingSoulrager copy() {
        return new RampagingSoulrager(this);
    }
}

enum RampagingSoulragerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return RampagingSoulragerValue.instance.calculate(game, source, null) >= 2;
    }
}

enum RampagingSoulragerValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ROOM);
    private static final Hint hint = new ValueHint("Unlocked doors among rooms you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .mapToInt(permanent -> (permanent.isLeftDoorUnlocked() ? 1 : 0) + (permanent.isRightDoorUnlocked() ? 1 : 0))
                .sum();
    }

    @Override
    public RampagingSoulragerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
