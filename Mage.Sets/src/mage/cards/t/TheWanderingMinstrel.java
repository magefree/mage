package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EnterUntappedAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.ElementalAllColorsToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWanderingMinstrel extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.TOWN, "you control five or more Towns"),
            ComparisonType.MORE_THAN, 4
    );
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.TOWN, "Towns you control"), null
    );
    private static final Hint hint = new ValueHint("Towns you control", xValue);

    public TheWanderingMinstrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Lands you control enter untapped.
        this.addAbility(new SimpleStaticAbility(new EnterUntappedAllEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS)));

        // The Minstrel's Ballad -- At the beginning of combat on your turn, if you control five or more Towns, create a 2/2 Elemental creature token that's all colors.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new CreateTokenEffect(new ElementalAllColorsToken())
        ).withInterveningIf(condition).withFlavorWord("The Minstrel's Ballad"));

        // {3}{W}{U}{B}{R}{G}: Other creatures you control get +X/+X until end of turn, where X is the number of Towns you control.
        this.addAbility(new SimpleActivatedAbility(
                new BoostControlledEffect(
                        xValue, xValue, Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURES, true
                ), new ManaCostsImpl<>("{3}{W}{U}{B}{R}{G}")
        ).addHint(hint));
    }

    private TheWanderingMinstrel(final TheWanderingMinstrel card) {
        super(card);
    }

    @Override
    public TheWanderingMinstrel copy() {
        return new TheWanderingMinstrel(this);
    }
}
