package mage.cards.f;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author FateRevoked
 */
public final class FinalPayment extends CardImpl {

    public FinalPayment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        // As an additional cost to cast this spell, pay 5 life or sacrifice a creature or enchantment.
        this.getSpellAbility().addCost(new OrCost(
                "pay 5 life or sacrifice a creature or enchantment", new PayLifeCost(5),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT)
        ));

        // Destroy target creature
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FinalPayment(final FinalPayment card) {
        super(card);
    }

    @Override
    public FinalPayment copy() {
        return new FinalPayment(this);
    }
}
