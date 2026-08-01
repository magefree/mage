package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GreenElfToken;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class DownInTheValley extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ELF, "Elves you control");

    public DownInTheValley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- Search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
            new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
            )
        );

        // II -- This Saga gains "Landfall -- Whenever a land you control enters, create a 1/1 green Elf creature token."
        Ability gainedAbility = new LandfallAbility(new CreateTokenEffect(new GreenElfToken()));
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
            new GainAbilitySourceEffect(gainedAbility, Duration.Custom)
                .setText("this Saga gains \"" + gainedAbility.getRule() + "\"")
        );

        // III, IV -- Elves you control get +1/+0 and gain vigilance until end of turn.
        Effects effects = new Effects();
        effects.add(new BoostControlledEffect(
            1, 0, Duration.EndOfTurn, filter
        ).setText("Elves you control get +1/+0"));
        effects.add(new GainAbilityControlledEffect(
            VigilanceAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and gain vigilance until end of turn"));
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_IV, effects
        );

        this.addAbility(sagaAbility);
    }

    private DownInTheValley(final DownInTheValley card) {
        super(card);
    }

    @Override
    public DownInTheValley copy() {
        return new DownInTheValley(this);
    }
}
