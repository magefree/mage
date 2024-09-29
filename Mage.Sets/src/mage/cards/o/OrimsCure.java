
package mage.cards.o;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author L_J
 */
public final class OrimsCure extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("If you control a Plains");
    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(SubType.PLAINS.getPredicate());
        filterCreature.add(TappedPredicate.UNTAPPED);
    }

    public OrimsCure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // If you control a Plains, you may tap an untapped creature you control rather than pay Orim's Cure's mana cost.
        Cost cost = new TapTargetCost(new TargetControlledPermanent(1,1,filterCreature,false));
        cost.setText("tap an untapped creature you control");
        this.addAbility(new AlternativeCostSourceAbility(cost, new PermanentsOnTheBattlefieldCondition(filter)));

        // Prevent the next 4 damage that would be dealt to any target this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private OrimsCure(final OrimsCure card) {
        super(card);
    }

    @Override
    public OrimsCure copy() {
        return new OrimsCure(this);
    }
}
