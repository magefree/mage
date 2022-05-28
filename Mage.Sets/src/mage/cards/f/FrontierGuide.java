
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class FrontierGuide extends CardImpl {

    public FrontierGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //{3}{G}, {T}: Search your library for a basic land card and put it onto the battlefield tapped. Then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                new ManaCostsImpl<>("{3}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FrontierGuide(final FrontierGuide card) {
        super(card);
    }

    @Override
    public FrontierGuide copy() {
        return new FrontierGuide(this);
    }
}
