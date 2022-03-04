package mage.cards.b;

import java.util.UUID;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBySubtypeCard;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author weirddan455
 */
public final class BindingTheOldGods extends CardImpl {

    private static final FilterNonlandPermanent filter
            = new FilterNonlandPermanent("nonland permanent an opponent controls");
    private static final FilterBySubtypeCard filter2
            = new FilterBySubtypeCard(SubType.FOREST);

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public BindingTheOldGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);
        // I — Destroy target nonland permanent an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new DestroyTargetEffect(), new TargetNonlandPermanent(filter)
        );
        // II — Search your library for a Forest card, put it onto the battlefield tapped, then shuffle your library.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter2), true)
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
