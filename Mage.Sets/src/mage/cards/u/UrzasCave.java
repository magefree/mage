package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
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
public final class UrzasCave extends CardImpl {

    public UrzasCave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.URZAS);
        this.subtype.add(SubType.CAVE);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}, Sacrifice Urza's Cave: Search your library for a land card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND_A), true
        ), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private UrzasCave(final UrzasCave card) {
        super(card);
    }

    @Override
    public UrzasCave copy() {
        return new UrzasCave(this);
    }
}
