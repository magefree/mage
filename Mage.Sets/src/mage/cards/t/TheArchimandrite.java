package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheArchimandrite extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            CardsInControllerHandCount.instance, StaticValue.get(-4)
    );
    private static final FilterPermanent filter = new FilterControlledPermanent();
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();
    private static final FilterControlledPermanent filter3
            = new FilterControlledPermanent("untapped Advisors, Artificers, and/or Monks you control");
    private static final Predicate<MageObject> predicate = Predicates.or(
            SubType.ADVISOR.getPredicate(),
            SubType.ARTIFICER.getPredicate(),
            SubType.MONK.getPredicate()
    );

    static {
        filter.add(predicate);
        filter2.add(predicate);
        filter3.add(predicate);
        filter3.add(TappedPredicate.UNTAPPED);
    }

    public TheArchimandrite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, you gain X life, where X is the number of cards in your hand minus 4.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new GainLifeEffect(xValue)
                        .setText("you gain X life, where X is the number of cards in your hand minus 4"),
                TargetController.YOU, false
        ));

        // Whenever you gain life, each Advisor, Artificer, and Monk you control gains vigilance and gets +X/+0 until end of turn, where X is the amount of life you gained.
        Ability ability = new GainLifeControllerTriggeredAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("each Advisor, Artificer, and Monk you control gains vigilance"));
        ability.addEffect(new BoostControlledEffect(
                TheArchimandriteValue.instance, StaticValue.get(0), Duration.EndOfTurn,
                filter2, false, true
        ).setText("and gets +X/+0 until end of turn, where X is the amount of life you gained"));
        this.addAbility(ability);

        // Tap three untapped Advisors, Artificers, and/or Monks you control: Draw a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new TapTargetCost(new TargetControlledPermanent(3, filter3))
        ));
    }

    private TheArchimandrite(final TheArchimandrite card) {
        super(card);
    }

    @Override
    public TheArchimandrite copy() {
        return new TheArchimandrite(this);
    }
}

enum TheArchimandriteValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue("gainedLife");
    }

    @Override
    public TheArchimandriteValue copy() {
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
