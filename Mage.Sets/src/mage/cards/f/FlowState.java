package mage.cards.f;

import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlowState extends CardImpl {

    private static final FilterCard filter1 = new FilterCard();
    private static final FilterCard filter2 = new FilterCard();

    static {
        filter1.add(CardType.INSTANT.getPredicate());
        filter2.add(CardType.SORCERY.getPredicate());
    }

    private static final Condition condition1 = new CardsInControllerGraveyardCondition(1, filter1);
    private static final Condition condition2 = new CardsInControllerGraveyardCondition(1, filter2);
    private static final Condition condition = new CompoundCondition(condition1, condition1);
    private static final Hint hint1 = new ConditionHint(condition1, "There's an instant card in your graveyard");
    private static final Hint hint2 = new ConditionHint(condition2, "There's a sorcery card in your graveyard");

    public FlowState(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order. If there is an instant card and a sorcery card in your graveyard, instead put two of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LookLibraryAndPickControllerEffect(3, 2, PutCards.HAND, PutCards.BOTTOM_ANY),
                new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.BOTTOM_ANY),
                condition, "look at the top three cards of your library. Put one of them into your hand " +
                "and the rest on the bottom of your library in any order. If there is an instant card " +
                "and a sorcery card in your graveyard, instead put two of them into your hand " +
                "and the rest on the bottom of your library in any order"
        ));
        this.getSpellAbility().addHint(hint1);
        this.getSpellAbility().addHint(hint2);
    }

    private FlowState(final FlowState card) {
        super(card);
    }

    @Override
    public FlowState copy() {
        return new FlowState(this);
    }
}
