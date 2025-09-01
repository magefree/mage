package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerpentOfThePass extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(3, new FilterCard(SubType.LESSON));
    private static final Hint hint = new ValueHint(
            "Lesson cards in your graveyard", new CardsInControllerGraveyardCount(new FilterCard(SubType.LESSON))
    );
    private static final FilterCard filter = new FilterCard("noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);
    private static final Hint hint2 = new ValueHint("Noncreature, nonland cards in your graveyard", xValue);

    public SerpentOfThePass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // If there are three or more Lesson cards in your graveyard, you may cast this spell as though it had flash.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConditionalAsThoughEffect(
                new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame), condition
        ).setText("if there are three or more Lesson cards in your graveyard, " +
                "you may cast this spell as though it had flash.")).addHint(hint));

        // This spell costs {1} less to cast for each noncreature, nonland card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).setRuleAtTheTop(true).addHint(hint2));
    }

    private SerpentOfThePass(final SerpentOfThePass card) {
        super(card);
    }

    @Override
    public SerpentOfThePass copy() {
        return new SerpentOfThePass(this);
    }
}
