package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoseijuReachesSkyward extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic Forest cards");
    private static final FilterCard filter2 = new FilterLandCard("land card from your graveyard");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.FOREST.getPredicate());
    }

    public BoseijuReachesSkyward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.b.BranchOfBoseiju.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Search your library for up to two basic Forest cards, reveal them, put them into your hand, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                        0, 2, filter
                ), true, true)
        );

        // II — Put up to one target land card from your graveyard on top of your library.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new PutOnLibraryTargetEffect(true),
                new TargetCardInYourGraveyard(0, 1, filter2)
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private BoseijuReachesSkyward(final BoseijuReachesSkyward card) {
        super(card);
    }

    @Override
    public BoseijuReachesSkyward copy() {
        return new BoseijuReachesSkyward(this);
    }
}
