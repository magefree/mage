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
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.hint.common.InitiativeHint;
import mage.abilities.hint.common.MonarchHint;
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

    private static final Condition energyCondition = new WowzerTheAspirationalCondition();
    private static final Condition bloodCondition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.BLOOD));
    private static final Condition clueCondition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.CLUE));
    private static final Condition foodCondition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.FOOD));
    private static final Condition mapCondition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.MAP));
    private static final Condition powerstoneCondition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.POWERSTONE));
    private static final Condition treasureCondition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.TREASURE));

    private static final Condition winCondition = new CompoundCondition(
            energyCondition,
            bloodCondition,
            clueCondition,
            foodCondition,
            mapCondition,
            powerstoneCondition,
            treasureCondition,
            MonarchIsSourceControllerCondition.instance,
            CitysBlessingCondition.instance,
            HaveInitiativeCondition.instance);

    private static final Hint energyHint = new ConditionHint(energyCondition, "You have an {E}");
    private static final Hint bloodHint = new ConditionHint(bloodCondition, "You control a Blood");
    private static final Hint clueHint = new ConditionHint(clueCondition, "You control a Clue");
    private static final Hint foodHint = new ConditionHint(foodCondition, "You control a Food");
    private static final Hint mapHint = new ConditionHint(mapCondition, "You control a Map");
    private static final Hint powerstoneHint
            = new ConditionHint(powerstoneCondition, "You control a Powerstone");
    private static final Hint treasureHint
            = new ConditionHint(treasureCondition, "You control a Treasure");

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
                winCondition,
                "Whenever {this} attacks, " +
                        "if you have an {E}, control a Blood, a Clue, a Food, a Map, a Powerstone, and a Treasure, " +
                        "are the monarch, and have the city's blessing and the initiative, you win the game."
        ).addHint(energyHint)
                .addHint(bloodHint)
                .addHint(clueHint)
                .addHint(foodHint)
                .addHint(mapHint)
                .addHint(powerstoneHint)
                .addHint(treasureHint)
                .addHint(MonarchHint.instance)
                .addHint(CitysBlessingHint.instance)
                .addHint(InitiativeHint.instance));
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
