package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.permanent.token.SmaugToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThereAndBackAgain extends CardImpl {

    private static final FilterCard filter = new FilterCard("Mountain card");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public ThereAndBackAgain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Up to one target creature can't block for as long as you control There and Back Again. The Ring tempts you.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new Effects(
                        new CantBlockTargetEffect(Duration.WhileControlled).setText("Up to one target creature can't block for as long as you control {this}"), new TheRingTemptsYouEffect()
                ), new TargetCreaturePermanent(0, 1)
        );

        // II -- Search your library for a Mountain card, put it onto the battlefield, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter))
        );

        // III -- Create Smaug, a legendary 6/6 red Dragon creature token with flying, haste, and "When this creature dies, create fourteen Treasure tokens."
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, new CreateTokenEffect(new SmaugToken())
        );

        this.addAbility(sagaAbility);
    }

    private ThereAndBackAgain(final ThereAndBackAgain card) {
        super(card);
    }

    @Override
    public ThereAndBackAgain copy() {
        return new ThereAndBackAgain(this);
    }
}
