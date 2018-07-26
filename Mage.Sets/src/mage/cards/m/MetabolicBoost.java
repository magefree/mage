package mage.cards.m;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class MetabolicBoost extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control");

    public MetabolicBoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");
        

        // Target creature gets +1/+1 until end of turn for each creature you control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(new PermanentsOnBattlefieldCount(filter), new PermanentsOnBattlefieldCount(filter), Duration.EndOfTurn)
                .setText("Target creature gets +1/+1 until end of turn for each creature you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public MetabolicBoost(final MetabolicBoost card) {
        super(card);
    }

    @Override
    public MetabolicBoost copy() {
        return new MetabolicBoost(this);
    }
}
