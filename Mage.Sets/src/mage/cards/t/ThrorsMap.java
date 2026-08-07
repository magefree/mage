package mage.cards.t;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ThrorsMap extends CardImpl {

    public ThrorsMap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Thror's Map enters, search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true)
        ));

        // {2}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
            new DrawDiscardControllerEffect(1, 1),
            new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ThrorsMap(final ThrorsMap card) {
        super(card);
    }

    @Override
    public ThrorsMap copy() {
        return new ThrorsMap(this);
    }
}
