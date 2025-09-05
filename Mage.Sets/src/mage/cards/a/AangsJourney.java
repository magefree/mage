package mage.cards.a;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardAndOrCardInLibrary;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangsJourney extends CardImpl {

    private static final Predicate<Card> predicate = Predicates.and(
            SuperType.BASIC.getPredicate(),
            CardType.LAND.getPredicate()
    );

    public AangsJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}");

        this.subtype.add(SubType.LESSON);

        // Kicker {2}
        this.addAbility(new KickerAbility("{2}"));

        // Search your library for a basic land card. If this spell was kicked, instead search your library for a basic land card and a Shrine card. Reveal those cards, put them into your hand, then shuffle.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardAndOrCardInLibrary(
                        predicate, SubType.SHRINE.getPredicate(),
                        "a basic land card and/or a Shrine card"
                ), true),
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                KickedCondition.ONCE, "search your library for a basic land card. If this spell was kicked, " +
                "instead search your library for a basic land card and a Shrine card. " +
                "Reveal those cards, put them into your hand, then shuffle"
        ));

        // You gain 2 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("<br>"));
    }

    private AangsJourney(final AangsJourney card) {
        super(card);
    }

    @Override
    public AangsJourney copy() {
        return new AangsJourney(this);
    }
}
