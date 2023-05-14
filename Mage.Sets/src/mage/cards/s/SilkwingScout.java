
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class SilkwingScout extends CardImpl {

    public SilkwingScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {G}, Sacrifice Silkwing Scout: Search your library for a basic land card and put that card onto the battlefield tapped. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true),
                new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SilkwingScout(final SilkwingScout card) {
        super(card);
    }

    @Override
    public SilkwingScout copy() {
        return new SilkwingScout(this);
    }
}
