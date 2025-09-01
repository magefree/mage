package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class BindingTheOldGods extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.FOREST);

    public BindingTheOldGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Destroy target nonland permanent an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new DestroyTargetEffect(), new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND)
        );

        // II — Search your library for a Forest card, put it onto the battlefield tapped, then shuffle your library.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true)
        );

        // III — Creatures you control gain deathtouch until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURES)
        );
        this.addAbility(sagaAbility);
    }

    private BindingTheOldGods(final BindingTheOldGods card) {
        super(card);
    }

    @Override
    public BindingTheOldGods copy() {
        return new BindingTheOldGods(this);
    }
}
