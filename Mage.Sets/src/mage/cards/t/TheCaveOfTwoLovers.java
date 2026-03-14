package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.AllyToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCaveOfTwoLovers extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Mountain or Cave card");

    static {
        filter.add(Predicates.or(
                SubType.MOUNTAIN.getPredicate(),
                SubType.CAVE.getPredicate()
        ));
    }

    public TheCaveOfTwoLovers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create two 1/1 white Ally creature tokens.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new CreateTokenEffect(new AllyToken(), 2)
        );

        // II -- Search your library for a Mountain or Cave card, reveal it, put it into your hand, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true
                ));

        // III -- Earthbend 3.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new EarthbendTargetEffect(3), new TargetControlledLandPermanent()
        );
        this.addAbility(sagaAbility);
    }

    private TheCaveOfTwoLovers(final TheCaveOfTwoLovers card) {
        super(card);
    }

    @Override
    public TheCaveOfTwoLovers copy() {
        return new TheCaveOfTwoLovers(this);
    }
}
