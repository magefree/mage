package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class VesselOfMalignity extends CardImpl {

    public VesselOfMalignity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // {1}{B}, Sacrifice Vessel of Malignity: Target opponent exiles two cards from their hand. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileFromZoneTargetEffect(
                        Zone.HAND, StaticFilters.FILTER_CARD_CARDS, 2, false
                ), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private VesselOfMalignity(final VesselOfMalignity card) {
        super(card);
    }

    @Override
    public VesselOfMalignity copy() {
        return new VesselOfMalignity(this);
    }
}
