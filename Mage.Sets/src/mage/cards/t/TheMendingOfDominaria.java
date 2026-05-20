package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.effects.common.ShuffleYourGraveyardIntoLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class TheMendingOfDominaria extends CardImpl {

    public TheMendingOfDominaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Put the top two cards of your library into your graveyard, then you may return a creature card from your graveyard to your hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new Effects(
                new MillCardsControllerEffect(2),
                new ReturnCardChosenFromGraveyardEffect(true,
                        StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, PutCards.HAND).concatBy(", then")
        ));

        // III — Return all land cards from your graveyard to the battlefield, then shuffle your graveyard into your library.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new ReturnFromYourGraveyardToBattlefieldAllEffect(StaticFilters.FILTER_CARD_LANDS),
                new ShuffleYourGraveyardIntoLibraryEffect().concatBy(", then")
        );
        this.addAbility(sagaAbility);
    }

    private TheMendingOfDominaria(final TheMendingOfDominaria card) {
        super(card);
    }

    @Override
    public TheMendingOfDominaria copy() {
        return new TheMendingOfDominaria(this);
    }
}
