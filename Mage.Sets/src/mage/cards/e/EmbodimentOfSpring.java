
package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
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

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class EmbodimentOfSpring extends CardImpl {

    public EmbodimentOfSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {1}{G}, {T}, Sacrifice Embodiment of Spring: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EmbodimentOfSpring(final EmbodimentOfSpring card) {
        super(card);
    }

    @Override
    public EmbodimentOfSpring copy() {
        return new EmbodimentOfSpring(this);
    }
}
