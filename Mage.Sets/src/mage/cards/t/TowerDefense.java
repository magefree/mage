
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Plopman
 */
public final class TowerDefense extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public TowerDefense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Creatures you control get +0/+5 and gain reach until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(0, 5, Duration.EndOfTurn, filter , false));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(ReachAbility.getInstance(), Duration.EndOfTurn, filter));
    }

    public TowerDefense(final TowerDefense card) {
        super(card);
    }

    @Override
    public TowerDefense copy() {
        return new TowerDefense(this);
    }
}
