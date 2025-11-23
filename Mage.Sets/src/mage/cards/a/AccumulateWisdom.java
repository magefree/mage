package mage.cards.a;

import mage.abilities.condition.common.LessonsInGraveCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AccumulateWisdom extends CardImpl {

    public AccumulateWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        this.subtype.add(SubType.LESSON);

        // Look at the top three cards of your library. Put one of those cards into your hand and the rest on the bottom of your library in any order. Put each of those cards into your hand instead if there are three or more Lesson cards in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LookLibraryAndPickControllerEffect(3, 3, PutCards.HAND, PutCards.BOTTOM_ANY),
                new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.BOTTOM_ANY),
                LessonsInGraveCondition.THREE, "look at the top three cards of your library. " +
                "Put one of those cards into your hand and the rest on the bottom of your library in any order. " +
                "Put each of those cards into your hand instead if there are three or more Lesson cards in your graveyard"
        ));
        this.getSpellAbility().addHint(LessonsInGraveCondition.getHint());
    }

    private AccumulateWisdom(final AccumulateWisdom card) {
        super(card);
    }

    @Override
    public AccumulateWisdom copy() {
        return new AccumulateWisdom(this);
    }
}
