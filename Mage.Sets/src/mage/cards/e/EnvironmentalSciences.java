package mage.cards.e;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnvironmentalSciences extends CardImpl {

    public EnvironmentalSciences(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}");

        this.subtype.add(SubType.LESSON);

        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle. You gain 2 life.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).setText("You gain 2 life"));
    }

    private EnvironmentalSciences(final EnvironmentalSciences card) {
        super(card);
    }

    @Override
    public EnvironmentalSciences copy() {
        return new EnvironmentalSciences(this);
    }
}
