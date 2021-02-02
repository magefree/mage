package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class SeismicAssault extends CardImpl {

    public SeismicAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}{R}");

        // Discard a land card: Seismic Assault deals 2 damage to any target.       
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2),
                new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A))
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SeismicAssault(final SeismicAssault card) {
        super(card);
    }

    @Override
    public SeismicAssault copy() {
        return new SeismicAssault(this);
    }
}
