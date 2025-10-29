package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GranGran extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(3, new FilterCard(SubType.LESSON));
    private static final Hint hint = new ValueHint(
            "Lesson cards in your graveyard", new CardsInControllerGraveyardCount(new FilterCard(SubType.LESSON))
    );

    public GranGran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Gran-Gran becomes tapped, draw a card, then discard a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // Noncreature spells you cast cost {1} less to cast as long as there are three or more Lesson cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new SpellsCostReductionControllerEffect(StaticFilters.FILTER_CARD_NON_CREATURE, 1),
                condition, "noncreature spells you cast cost {1} less to cast as long as " +
                "there are three or more Lesson cards in your graveyard"
        )).addHint(hint));
    }

    private GranGran(final GranGran card) {
        super(card);
    }

    @Override
    public GranGran copy() {
        return new GranGran(this);
    }
}
