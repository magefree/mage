
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
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
 * @author Plopman
 */
public final class DiligentFarmhand extends CardImpl {

    public DiligentFarmhand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, Sacrifice Diligent Farmhand: Search your library for a basic land card and put that card onto the battlefield tapped. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
        // If Diligent Farmhand is in a graveyard, effects from spells named Muscle Burst count it as a card named Muscle Burst.
        this.addAbility(mage.cards.m.MuscleBurst.getCountAsAbility());
    }

    private DiligentFarmhand(final DiligentFarmhand card) {
        super(card);
    }

    @Override
    public DiligentFarmhand copy() {
        return new DiligentFarmhand(this);
    }
}