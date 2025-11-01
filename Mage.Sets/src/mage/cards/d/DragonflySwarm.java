package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonflySwarm extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature, nonland cards");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);
    private static final Condition condition = new CompoundCondition(
            "there's a Lesson card in your graveyard",
            new CardsInControllerGraveyardCondition(1, new FilterCard(SubType.LESSON))
    );
    private static final Hint hint = new ConditionHint(condition);

    public DragonflySwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // This creature's power is equal to the number of noncreature, nonland cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(xValue)));

        // When this creature dies, if there's a Lesson card in your graveyard, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(condition).addHint(hint));
    }

    private DragonflySwarm(final DragonflySwarm card) {
        super(card);
    }

    @Override
    public DragonflySwarm copy() {
        return new DragonflySwarm(this);
    }
}
