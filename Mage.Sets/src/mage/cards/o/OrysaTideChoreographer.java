package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrysaTideChoreographer extends CardImpl {

    public OrysaTideChoreographer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This spell costs {3} less to cast if creatures you control have total toughness 10 or greater.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, OrysaTideChoreographerCondition.instance)
        ).setRuleAtTheTop(true).addHint(OrysaTideChoreographerValue.getHint()));

        // When Orysa enters, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2)));
    }

    private OrysaTideChoreographer(final OrysaTideChoreographer card) {
        super(card);
    }

    @Override
    public OrysaTideChoreographer copy() {
        return new OrysaTideChoreographer(this);
    }
}

enum OrysaTideChoreographerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return OrysaTideChoreographerValue.instance.calculate(game, source, null) >= 10;
    }

    @Override
    public String toString() {
        return "creatures you control have total toughness 10 or greater";
    }
}

enum OrysaTideChoreographerValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Total toughness of creatures you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURES,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .map(MageObject::getToughness)
                .mapToInt(MageInt::getValue)
                .sum();
    }

    @Override
    public OrysaTideChoreographerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}
