package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TuyaBearclaw extends CardImpl {

    public TuyaBearclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Tuya Bearclaw attacks, it gets +X/+X until end of turn, where X is the greatest power among other creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                TuyaBearclawValue.instance,
                TuyaBearclawValue.instance,
                Duration.EndOfTurn, true
        ), false));
    }

    private TuyaBearclaw(final TuyaBearclaw card) {
        super(card);
    }

    @Override
    public TuyaBearclaw copy() {
        return new TuyaBearclaw(this);
    }
}

enum TuyaBearclawValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                ).stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public TuyaBearclawValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "the greatest power among other creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
