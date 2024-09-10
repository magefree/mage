package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheRestorationOfEiganjo extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("a basic Plains card");
    private static final FilterCard filter2
            = new FilterPermanentCard("permanent card with mana value 2 or less from your graveyard");

    static {
        filter.add(SubType.PLAINS.getPredicate());
        filter.add(SuperType.BASIC.getPredicate());
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public TheRestorationOfEiganjo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.a.ArchitectOfRestoration.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I - Search your library for a basic Plains card, reveal it, put it into your hand, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(filter), true
                )
        );

        // II — You may discard a card. When you do, return target permanent card with mana value 2 or less from your graveyard to the battlefield tapped
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, new DoWhenCostPaid(
                        ability, new DiscardCardCost(), "Discard a card?"
                )
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private TheRestorationOfEiganjo(final TheRestorationOfEiganjo card) {
        super(card);
    }

    @Override
    public TheRestorationOfEiganjo copy() {
        return new TheRestorationOfEiganjo(this);
    }
}
