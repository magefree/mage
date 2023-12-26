package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnFromGraveyardAtRandomEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class HauntedFengraf extends CardImpl {

    public HauntedFengraf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {tap}, Sacrifice Haunted Fengraf: Return a creature card at random from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardAtRandomEffect(
                StaticFilters.FILTER_CARD_CREATURE, Zone.HAND), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private HauntedFengraf(final HauntedFengraf card) {
        super(card);
    }

    @Override
    public HauntedFengraf copy() {
        return new HauntedFengraf(this);
    }
}
