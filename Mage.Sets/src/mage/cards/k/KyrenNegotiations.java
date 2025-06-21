package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author ingmargoudt
 */
public final class KyrenNegotiations extends CardImpl {

    public KyrenNegotiations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Tap an untapped creature you control: Kyren Negotiations deals 1 damage to target player.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1), new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)
        );
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private KyrenNegotiations(final KyrenNegotiations card) {
        super(card);
    }

    @Override
    public KyrenNegotiations copy() {
        return new KyrenNegotiations(this);
    }
}
