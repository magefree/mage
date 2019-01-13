package mage.cards.f;

import java.util.UUID;

import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author FateRevoked
 */
public final class FinalPayment extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a creature or enchantment");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public FinalPayment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        // As an additional cost to cast this spell, pay 5 life or sacrifice a creature or enchantment.
        final Cost lifeCost = new PayLifeCost(5);
        final Cost sacrificeCost = new SacrificeTargetCost(new TargetControlledPermanent(filter));

        this.getSpellAbility().addCost(new OrCost(lifeCost, sacrificeCost,
                "pay 5 life or sacrifice a creature or enchantment"));

        // Destroy target creature
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public FinalPayment(final FinalPayment card) {
        super(card);
    }

    @Override
    public FinalPayment copy() {
        return new FinalPayment(this);
    }
}
