package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.*;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class RaynorRebelCommander extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public RaynorRebelCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{R}{W}");
        
        this.subtype.add(SubType.RAYNOR);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(2));

        // +1: You may discard a card. If you do, draw a card.
        this.addAbility(new LoyaltyAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), 1));

        // -2: Raynor, Rebel Commander deals 2 damage to target creature and you gain 2 life.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(2), -2);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);

        // -5: Destroy all creatures with power 3 or greater.
        this.addAbility(new LoyaltyAbility(new DestroyAllEffect(filter), -5));
    }

    public RaynorRebelCommander(final RaynorRebelCommander card) {
        super(card);
    }

    @Override
    public RaynorRebelCommander copy() {
        return new RaynorRebelCommander(this);
    }
}
