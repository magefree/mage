
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author anonymous
 */
public final class TerminalMoraine extends CardImpl {

    public TerminalMoraine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}, {tap}, Sacrifice Terminal Moraine: Search your library for a basic land card and put that card onto the battlefield tapped. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TerminalMoraine(final TerminalMoraine card) {
        super(card);
    }

    @Override
    public TerminalMoraine copy() {
        return new TerminalMoraine(this);
    }
}
