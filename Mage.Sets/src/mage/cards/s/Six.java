package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.continuous.GainRetraceYourGraveyardEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Six extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("nonland permanent card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public Six(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Six attacks, mill three cards. You may put a land card from among them into your hand.
        this.addAbility(new AttacksTriggeredAbility(
                new MillThenPutInHandEffect(3, StaticFilters.FILTER_CARD_LAND_A)
                        .withTextOptions("them")
        ));

        // As long as it's your turn, nonland permanent cards in your graveyard have retrace.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new GainRetraceYourGraveyardEffect(filter),
                        MyTurnCondition.instance,
                        "As long as it's your turn, nonland permanent cards in your graveyard have retrace."
                )
        ).addHint(MyTurnHint.instance));
    }

    private Six(final Six card) {
        super(card);
    }

    @Override
    public Six copy() {
        return new Six(this);
    }
}