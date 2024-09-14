package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.condition.common.HaveInitiativeCondition;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public class WowzerTheAspirational extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            new WowzerTheAspirationalCondition(),
            new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.BLOOD)),
            new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.CLUE)),
            new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.FOOD)),
            new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.MAP)),
            new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.POWERSTONE)),
            new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.TREASURE)),
            MonarchIsSourceControllerCondition.instance,
            CitysBlessingCondition.instance,
            HaveInitiativeCondition.instance);
    private static final Hint hint = new ConditionHint(
            condition,
            "You have an {E}, control a Blood, a Clue, a Food, a Map, a Powerstone, and a Treasure, " +
                    "are the monarch, and have the city's blessing and the initiative");

    public WowzerTheAspirational(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{C}{W}{U}{B}{R}{G}{S}");

        this.supertype.add(SuperType.LEGENDARY);
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Whenever Wowzer, the Aspirational attacks,
        // if you have an {E}, control a Blood, a Clue, a Food, a Map, a Powerstone, and a Treasure,
        // are the monarch, and have the city's blessing and the initiative, you win the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new WinGameSourceControllerEffect()),
                condition,
                "Whenever {this} attacks, " +
                        "if you have an {E}, control a Blood, a Clue, a Food, a Map, a Powerstone, and a Treasure, " +
                        "are the monarch, and have the city's blessing and the initiative, you win the game."
        ).addHint(hint));
    }

    private WowzerTheAspirational(final WowzerTheAspirational card) {
        super(card);
    }

    @Override
    public WowzerTheAspirational copy() {
        return new WowzerTheAspirational(this);
    }
}

class WowzerTheAspirationalCondition extends IntCompareCondition {

    WowzerTheAspirationalCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        return new CountersControllerCount(CounterType.ENERGY).calculate(game, source, null);
    }
}
