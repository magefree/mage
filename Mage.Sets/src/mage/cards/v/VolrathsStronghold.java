
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class VolrathsStronghold extends CardImpl {

    public VolrathsStronghold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.LEGENDARY);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}{B}, {tap}: Put target creature card from your graveyard on top of your library.
        Ability ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(true), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private VolrathsStronghold(final VolrathsStronghold card) {
        super(card);
    }

    @Override
    public VolrathsStronghold copy() {
        return new VolrathsStronghold(this);
    }
}
