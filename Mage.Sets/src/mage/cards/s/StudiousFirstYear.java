package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StudiousFirstYear extends PrepareCard {

    public StudiousFirstYear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}", "Rampant Growth", new CardType[]{CardType.SORCERY}, "{1}{G}");

        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Rampant Growth
        // Sorcery {1}{G}
        // Search your library for a basic land card, put that card onto the battlefield tapped, then shuffle.
        this.getSpellCard().getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true
        ));
    }

    private StudiousFirstYear(final StudiousFirstYear card) {
        super(card);
    }

    @Override
    public StudiousFirstYear copy() {
        return new StudiousFirstYear(this);
    }
}
