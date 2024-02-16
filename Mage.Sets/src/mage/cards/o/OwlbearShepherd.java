package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OwlbearShepherd extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Total power and toughness of creatures you control", OwlbearShepherdValue.instance
    );

    public OwlbearShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, if creatures you control have total power and toughness 8 or greater, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                TargetController.YOU, FormidableCondition.instance, false
        ).addHint(hint));
    }

    private OwlbearShepherd(final OwlbearShepherd card) {
        super(card);
    }

    @Override
    public OwlbearShepherd copy() {
        return new OwlbearShepherd(this);
    }
}

enum OwlbearShepherdValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                ).stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
    }

    @Override
    public OwlbearShepherdValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}
