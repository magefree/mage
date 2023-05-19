package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoseijuReachesSkyward extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("basic Forest cards");
    private static final FilterCard filter2 = new FilterLandCard("land card from your graveyard");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.FOREST.getPredicate());
    }

    public BoseijuReachesSkyward(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{3}{G}",
                "Branch of Boseiju",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.PLANT}, "G"
        );
        this.getRightHalfCard().setPT(0, 0);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Search your library for up to two basic Forest cards, reveal them, put them into your hand, then shuffle.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                        0, 2, filter
                ), true, true)
        );

        // II — Put up to one target land card from your graveyard on top of your library.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new PutOnLibraryTargetEffect(true),
                new TargetCardInYourGraveyard(0, 1, filter2)
        );

        // III — Exile th is Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_III,
                new ExileSagaAndReturnTransformedEffect()
        );

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Branch of Boseiju
        // Reach
        this.getRightHalfCard().addAbility(ReachAbility.getInstance());

        // Branch of Boseiju gets +1/+1 for each land you control.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                LandsYouControlCount.instance, LandsYouControlCount.instance, Duration.WhileOnBattlefield
        ).setText("{this} gets +1/+1 for each land you control")));
    }

    private BoseijuReachesSkyward(final BoseijuReachesSkyward card) {
        super(card);
    }

    @Override
    public BoseijuReachesSkyward copy() {
        return new BoseijuReachesSkyward(this);
    }
}
