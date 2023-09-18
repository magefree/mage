package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class TheCrueltyOfGix extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or planeswalker card");
    private static final FilterCreatureCard filter2 = new FilterCreatureCard("creature card from a graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public TheCrueltyOfGix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I -- Target opponent reveals their hand. You choose a creature or planeswalker card from it. That player discards that card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new DiscardCardYouChooseTargetEffect(filter), new TargetOpponent()
        );

        // II -- Search your library for a card, put that card into your hand, then shuffle. You lose 3 life.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true),
                new LoseLifeSourceControllerEffect(3)
        );

        // III -- Put target creature card from a graveyard onto the battlefield under your control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new TargetCardInGraveyard(filter2)
        );
        this.addAbility(sagaAbility);
    }

    private TheCrueltyOfGix(final TheCrueltyOfGix card) {
        super(card);
    }

    @Override
    public TheCrueltyOfGix copy() {
        return new TheCrueltyOfGix(this);
    }
}
