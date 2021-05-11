package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author ingmargoudt
 */
public final class KyrenNegotiations extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public KyrenNegotiations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Tap an untapped creature you control: Kyren Negotiations deals 1 damage to target player.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(1),
                new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false))
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
