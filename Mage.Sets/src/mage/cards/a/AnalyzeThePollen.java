package mage.cards.a;

import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.CollectEvidenceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnalyzeThePollen extends CardImpl {

    public AnalyzeThePollen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // As an additional cost to cast this spell, you may collect evidence 8.
        this.addAbility(new CollectEvidenceAbility(8));

        // Search your library for a basic land card. If evidence was collected, instead search your library for a creature or land card. Reveal that card, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE_OR_LAND), true),
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                CollectedEvidenceCondition.instance, "search your library for a basic land card. " +
                "If evidence was collected, instead search your library for a creature or land card. " +
                "Reveal that card, put it into your hand, then shuffle"
        ));
    }

    private AnalyzeThePollen(final AnalyzeThePollen card) {
        super(card);
    }

    @Override
    public AnalyzeThePollen copy() {
        return new AnalyzeThePollen(this);
    }
}
