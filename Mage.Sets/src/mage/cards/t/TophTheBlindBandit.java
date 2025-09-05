package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TophTheBlindBandit extends CardImpl {

    public TophTheBlindBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Toph enters, earthbend 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(2));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);

        // Toph's power is equal to the number of +1/+1 counters on lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(TophTheBlindBanditValue.instance)
        ).addHint(TophTheBlindBanditValue.getHint()));
    }

    private TophTheBlindBandit(final TophTheBlindBandit card) {
        super(card);
    }

    @Override
    public TophTheBlindBandit copy() {
        return new TophTheBlindBandit(this);
    }
}

enum TophTheBlindBanditValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("+1/+1 counters on lands you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .map(permanent -> permanent.getCounters(game))
                .mapToInt(counters -> counters.getCount(CounterType.P1P1))
                .sum();
    }

    @Override
    public TophTheBlindBanditValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "+1/+1 counters on lands you control";
    }

    @Override
    public String toString() {
        return "1";
    }
}
