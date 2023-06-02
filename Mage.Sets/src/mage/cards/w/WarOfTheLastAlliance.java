package mage.cards.w;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarOfTheLastAlliance extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("legendary creature card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public WarOfTheLastAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Search your library for a legendary creature card, reveal it, put it into your hand, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        );

        // III -- Creatures you control gain double strike until end of turn. The Ring tempts you.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new GainAbilityControlledEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURES
                ), new TheRingTemptsYouEffect()
        );

        this.addAbility(sagaAbility);
    }

    private WarOfTheLastAlliance(final WarOfTheLastAlliance card) {
        super(card);
    }

    @Override
    public WarOfTheLastAlliance copy() {
        return new WarOfTheLastAlliance(this);
    }
}
