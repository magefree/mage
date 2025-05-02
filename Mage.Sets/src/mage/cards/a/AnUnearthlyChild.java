package mage.cards.a;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnUnearthlyChild extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Doctor card, a card with doctor's companion, or a Vehicle card");

    static {
        filter.add(Predicates.or(
                SubType.DOCTOR.getPredicate(),
                new AbilityPredicate(DoctorsCompanionAbility.class),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public AnUnearthlyChild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II, III -- Reveal cards from the top of your library until you reveal a Doctor card, a card with doctor's companion, or a Vehicle card. Put that card into your hand and the rest on the bottom of your library in a random order.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new RevealCardsFromLibraryUntilEffect(filter, PutCards.HAND, PutCards.BOTTOM_RANDOM)
        );

        this.addAbility(sagaAbility);
    }

    private AnUnearthlyChild(final AnUnearthlyChild card) {
        super(card);
    }

    @Override
    public AnUnearthlyChild copy() {
        return new AnUnearthlyChild(this);
    }
}
