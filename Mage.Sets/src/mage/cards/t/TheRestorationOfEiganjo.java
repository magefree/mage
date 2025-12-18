package mage.cards.t;

import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.SpiritToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheRestorationOfEiganjo extends TransformingDoubleFacedCard {

    private static final FilterCard filter2
            = new FilterPermanentCard("permanent card with mana value 2 or less from your graveyard");

    static {
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public TheRestorationOfEiganjo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{2}{W}",
                "Architect of Restoration",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.FOX, SubType.MONK}, "W"
        );

        // The Restoration of Eiganjo
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I - Search your library for a basic Plains card, reveal it, put it into your hand, then shuffle.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_PLAINS), true
                )
        );

        // II — You may discard a card. When you do, return target permanent card with mana value 2 or less from your graveyard to the battlefield tapped
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_II, new DoWhenCostPaid(
                        ability, new DiscardCardCost(), "Discard a card?"
                )
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Architect of Restoration
        this.getRightHalfCard().setPT(3, 4);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Whenever Architect of Restoration attacks or blocks, create a 1/1 colorless Spirit creature token.
        this.getRightHalfCard().addAbility(new AttacksOrBlocksTriggeredAbility(new CreateTokenEffect(new SpiritToken()), false));
    }

    private TheRestorationOfEiganjo(final TheRestorationOfEiganjo card) {
        super(card);
    }

    @Override
    public TheRestorationOfEiganjo copy() {
        return new TheRestorationOfEiganjo(this);
    }
}
