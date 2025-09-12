package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheRiseOfSozin extends CardImpl {

    public TheRiseOfSozin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.f.FireLordSozin.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Destroy all creatures.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES)
        );

        // II -- Choose a card name. Search target opponent's graveyard, hand, and library for up to four cards with that name and exile them. Then that player shuffles.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new Effects(
                        new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL), new TheRiseOfSozinEffect()
                ), new TargetOpponent()
        );

        // III -- Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.addAbility(sagaAbility);
    }

    private TheRiseOfSozin(final TheRiseOfSozin card) {
        super(card);
    }

    @Override
    public TheRiseOfSozin copy() {
        return new TheRiseOfSozin(this);
    }
}

class TheRiseOfSozinEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    TheRiseOfSozinEffect() {
        super(true, "target opponent's", "up to four cards with that name", false, 4);
    }

    private TheRiseOfSozinEffect(final TheRiseOfSozinEffect effect) {
        super(effect);
    }

    @Override
    public TheRiseOfSozinEffect copy() {
        return new TheRiseOfSozinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String chosenCardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        return applySearchAndExile(game, source, chosenCardName, getTargetPointer().getFirst(game, source));
    }
}
