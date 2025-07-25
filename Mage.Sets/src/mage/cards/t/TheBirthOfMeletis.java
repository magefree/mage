package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ArtifactWallToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBirthOfMeletis extends CardImpl {

    public TheBirthOfMeletis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Search your library for a basic Plains card, reveal it, put it into your hand, then shuffle your library.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_PLAINS), true
                )
        );

        // II — Create a colorless 0/4 Wall artifact creature token with defender.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, new CreateTokenEffect(new ArtifactWallToken())
        );

        // III — You gain 2 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new GainLifeEffect(2));
        this.addAbility(sagaAbility);
    }

    private TheBirthOfMeletis(final TheBirthOfMeletis card) {
        super(card);
    }

    @Override
    public TheBirthOfMeletis copy() {
        return new TheBirthOfMeletis(this);
    }
}
